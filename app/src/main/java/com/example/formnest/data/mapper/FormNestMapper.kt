package com.example.formnest.data.mapper

import com.example.formnest.data.model.FormNestApiModel
import com.example.formnest.domain.model.ContentItemDomain

fun FormNestApiModel.toDomain(): ContentItemDomain? {
    return when (type) {
        "page" -> {
            if (title != null) {
                ContentItemDomain.Page(
                    title = title,
                    items = items?.mapNotNull { it.toDomain() }.orEmpty()
                )
            } else null
        }

        "section" -> {
            if (title != null) {
                ContentItemDomain.Section(
                    title = title,
                    items = items?.mapNotNull { it.toDomain() }.orEmpty()
                )
            } else null
        }

        "text" -> {
            title?.let { ContentItemDomain.Text(it) }
        }

        "image" -> {
            if (title != null && src != null) {
                ContentItemDomain.Image(title, src)
            } else null
        }

        else -> null
    }
}
