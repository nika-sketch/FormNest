package com.example.formnest.data.model


import androidx.annotation.Keep

@Keep
data class ItemX(
    val items: List<ItemXX>?,
    val src: String?,
    val title: String,
    val type: String
)
