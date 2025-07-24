package com.example.formnest.presentation.hierarchy.model

sealed class HierarchyScreenState {

    data class Success(val hierarchyList: List<HierarchyRecursiveItemUi>) : HierarchyScreenState()

    data class Error(val message: String) : HierarchyScreenState()

    data object Loading : HierarchyScreenState()
}
