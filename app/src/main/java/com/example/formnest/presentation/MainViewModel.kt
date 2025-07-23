package com.example.formnest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formnest.domain.model.ContentItemDomain
import com.example.formnest.domain.repository.FormNestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val formNestRepository: FormNestRepository
) : ViewModel() {

    private val _state = MutableStateFlow<List<RenderableItem>>(emptyList())
    val state: StateFlow<List<RenderableItem>> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            formNestRepository.surveyData()
                .onSuccess { contentItem ->
                    _state.value = flattenContentItem(contentItem)
                }.onFailure { error ->
                    _state.value = listOf(
                        RenderableItem(
                            item = ContentItemDomain.Text(title = "Error: ${error.message}"),
                            level = 0
                        )
                    )
                }
        }
    }

    private fun flattenContentItem(root: ContentItemDomain): List<RenderableItem> = buildList {
        fun traverse(item: ContentItemDomain, level: Int) {
            add(RenderableItem(item, level))
            when (item) {
                is ContentItemDomain.Page -> item.items.forEach { traverse(it, level + 1) }
                is ContentItemDomain.Section -> item.items.forEach { traverse(it, level + 1) }
                else -> Unit
            }
        }

        traverse(root, 0)
    }
}


data class RenderableItem(
    val item: ContentItemDomain,
    val level: Int
)
