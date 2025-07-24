package com.example.formnest.presentation.hierarchy.model

sealed interface HierarchyContentUi {

    data class Page(
        val title: String,
        val items: List<HierarchyContentUi>,
    ) : HierarchyContentUi

    data class Section(
        val title: String, val items: List<HierarchyContentUi>
    ) : HierarchyContentUi

    data class Text(val title: String) : HierarchyContentUi

    data class Image(val title: String, val src: String) : HierarchyContentUi
}
