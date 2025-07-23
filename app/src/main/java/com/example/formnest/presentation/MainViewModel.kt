package com.example.formnest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.presentation.model.ContentItemUi
import com.example.formnest.presentation.model.RendererItemUi
import com.example.formnest.shared.DispatcherProvider
import com.example.formnest.shared.Mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val formNestRepository: FormNestRepository,
    private val contentMapper: Mapper<FormNestDomain, ContentItemUi>,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow<List<RendererItemUi>>(emptyList())
    val state: StateFlow<List<RendererItemUi>> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            formNestRepository.surveyData().onSuccess { contentItemDomain ->
                withContext(dispatchers.main()) {
                    val contentUi = contentMapper.map(contentItemDomain)
                    _state.value = flattenContentItem(contentUi)
                }
            }.onFailure { error ->
                _state.value = listOf(
                    RendererItemUi(
                        item = ContentItemUi.Text(title = "Error: ${error.message}"),
                        level = 0
                    )
                )
            }
        }
    }

    private fun flattenContentItem(root: ContentItemUi): List<RendererItemUi> = buildList {
        fun traverse(item: ContentItemUi, level: Int) {
            add(RendererItemUi(item, level))
            when (item) {
                is ContentItemUi.Page -> item.items.forEach { traverse(it, level + 1) }
                is ContentItemUi.Section -> item.items.forEach { traverse(it, level + 1) }
                else -> Unit
            }
        }

        traverse(root, 0)
    }
}

