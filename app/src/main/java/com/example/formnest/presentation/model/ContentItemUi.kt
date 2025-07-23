package com.example.formnest.presentation.model

sealed interface ContentItemUi {

    data class Page(
        val title: String,
        val items: List<ContentItemUi>,
    ) : ContentItemUi

    data class Section(
        val title: String,
        val items: List<ContentItemUi>,
    ) : ContentItemUi

    data class Text(val title: String) : ContentItemUi

    data class Image(val title: String, val src: String) : ContentItemUi
}