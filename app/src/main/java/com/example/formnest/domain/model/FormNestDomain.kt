package com.example.formnest.domain.model

sealed interface FormNestDomain {

    data class Page(
        val title: String,
        val items: List<FormNestDomain>
    ) : FormNestDomain

    data class Section(
        val title: String,
        val items: List<FormNestDomain>
    ) : FormNestDomain

    data class Text(
        val title: String
    ) : FormNestDomain

    data class Image(
        val title: String, val src: String
    ) : FormNestDomain
}
