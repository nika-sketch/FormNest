package com.example.formnest.presentation.navigation.model

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object MainScreen : NavKey

data class ImageDetail(
    val title: String,
    val imageUrl: String
) : NavKey
