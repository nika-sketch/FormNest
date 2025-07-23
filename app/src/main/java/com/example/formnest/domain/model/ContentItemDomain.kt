package com.example.formnest.domain.model

sealed interface ContentItemDomain {

    data class Page(
        val title: String,
        val items: List<ContentItemDomain>
    ) : ContentItemDomain

    data class Section(
        val title: String,
        val items: List<ContentItemDomain>
    ) : ContentItemDomain

    data class Text(
        val title: String
    ) : ContentItemDomain

    data class Image(
        val title: String, val src: String
    ) : ContentItemDomain
}
