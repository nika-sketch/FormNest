package com.example.formnest.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.formnest.data.model.FormNestApiModel
import com.example.formnest.di.NetworkModule
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
            try {
                val result = networkModule.provideFormNestService().fetchSurveyData()
                mutableState.value = result.body() ?: error("Body is null")
            } catch (e: Exception) {
                // TODO handle error and coroutine cancellation
            }
        }
    }
}
