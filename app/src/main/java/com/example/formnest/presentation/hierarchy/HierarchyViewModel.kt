package com.example.formnest.presentation.hierarchy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.presentation.hierarchy.model.HierarchyContentUi
import com.example.formnest.presentation.hierarchy.model.HierarchyScreenState
import com.example.formnest.presentation.hierarchy.model.HierarchyRecursiveItemUi
import com.example.formnest.shared.ConnectivityObserver
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
  private val dispatchers: DispatcherProvider,
  private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

  private val _hierarchyScreenState = MutableStateFlow<HierarchyScreenState>(
    HierarchyScreenState.Initial
  )
  val hierarchyScreenState: StateFlow<HierarchyScreenState> = _hierarchyScreenState.asStateFlow()

  private val triggerRefreshFlow = MutableSharedFlow<Unit>(replay = 1)

  init {
    surveyData()
    observeConnection()
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

  private fun surveyData() = viewModelScope.launch {
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
        // TODO handle error with user friendly message depending on exception type
        _hierarchyScreenState.value = HierarchyScreenState.Error(error.message.toString())
      }
    }
  }

  private fun observeConnection() = viewModelScope.launch {
    connectivityObserver.isConnectedFlow.collect { isConnected ->
      val currentState = hierarchyScreenState.value
      if (currentState !is HierarchyScreenState.Success && isConnected) refresh()
    }
  }
}
