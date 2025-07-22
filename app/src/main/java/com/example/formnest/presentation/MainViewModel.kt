package com.example.formnest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formnest.data.model.FormNestApiModel
import com.example.formnest.di.NetworkModule
import com.example.formnest.shared.runCatchingCancellable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val networkModule: NetworkModule
) : ViewModel() {

    private val mutableState = MutableStateFlow<FormNestApiModel>(
        FormNestApiModel(emptyList(), "", "")
    )
    val state: StateFlow<FormNestApiModel> = mutableState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            runCatchingCancellable {
                networkModule.provideFormNestService().fetchSurveyData()
            }.onSuccess { response ->
                mutableState.value = response.body() ?: error("Body is null")
            }.onFailure { e ->
                mutableState.value = FormNestApiModel(emptyList(), "", "")
            }
        }
    }
}
