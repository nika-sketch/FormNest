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

    private val mutableState = MutableStateFlow<ContentItemDomain>(
        ContentItemDomain.Text(title = "Text")
    )
    val state: StateFlow<ContentItemDomain> = mutableState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            formNestRepository.surveyData().onSuccess { contentItem ->
                mutableState.value = contentItem
            }.onFailure { error ->
                // TODO handle error
                mutableState.value = ContentItemDomain.Text(title = "Error: ${error.message}")
            }
        }
    }
}
