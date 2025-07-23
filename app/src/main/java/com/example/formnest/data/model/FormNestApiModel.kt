package com.example.formnest.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FormNestApiModel(
    val type: String,
    val title: String?,
    val src: String?,
    val items: List<FormNestApiModel>? = null
)
