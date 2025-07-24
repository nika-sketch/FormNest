package com.example.formnest.presentation.hierarchy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.presentation.hierarchy.model.HierarchyContentUi
import com.example.formnest.presentation.hierarchy.model.HierarchyScreenState
import com.example.formnest.presentation.hierarchy.model.HierarchyRecursiveItemUi
import com.example.formnest.shared.DispatcherProvider
import com.example.formnest.shared.Mapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HierarchyViewModel(
  private val formNestRepository: FormNestRepository,
  private val contentMapper: Mapper<FormNestDomain, HierarchyContentUi>,
  private val dispatchers: DispatcherProvider
) : ViewModel() {

  private val _hierarchyScreenState = MutableStateFlow<HierarchyScreenState>(
    HierarchyScreenState.Initial
  )
  val hierarchyScreenState: StateFlow<HierarchyScreenState> = _hierarchyScreenState.asStateFlow()

  private val triggerRefreshFlow = MutableSharedFlow<Unit>(replay = 1)

  init {
    viewModelScope.launch {
      triggerRefreshFlow.collectLatest {
        _hierarchyScreenState.value = HierarchyScreenState.Loading
        formNestRepository.surveyData().onSuccess { contentItemDomain ->
          withContext(dispatchers.main()) {
            val contentUi = contentMapper.map(contentItemDomain)
            _hierarchyScreenState.value = HierarchyScreenState.Success(
              hierarchyList = hierarchyList(contentUi)
            )
          }
        }.onFailure { error ->
          _hierarchyScreenState.value = HierarchyScreenState.Error(error.message.toString())
        }
      }
    }
    triggerRefreshFlow.tryEmit(Unit)
  }

  fun refresh() = triggerRefreshFlow.tryEmit(Unit)

  private fun hierarchyList(root: HierarchyContentUi): List<HierarchyRecursiveItemUi> = buildList {
    fun fillHierarchyListRecursive(item: HierarchyContentUi, level: Int) {
      add(HierarchyRecursiveItemUi(item, level))
      when (item) {
        is HierarchyContentUi.Page -> item.items.forEach {
          fillHierarchyListRecursive(it, level + 1)
        }

        is HierarchyContentUi.Section -> item.items.forEach {
          fillHierarchyListRecursive(it, level + 1)
        }

        else -> Unit
      }
    }

    fillHierarchyListRecursive(root, 0)
  }
}
