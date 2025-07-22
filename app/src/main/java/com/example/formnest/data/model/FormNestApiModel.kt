package com.example.formnest.data.model


import androidx.annotation.Keep

@Keep
data class FormNestApiModel(
    val items: List<Item>,
    val title: String,
    val type: String
)
