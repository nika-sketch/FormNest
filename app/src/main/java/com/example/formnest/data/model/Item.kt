package com.example.formnest.data.model


import androidx.annotation.Keep

@Keep
data class Item(
    val items: List<ItemX>,
    val title: String,
    val type: String
)
