package com.example.formnest.presentation.mapper

import com.example.formnest.domain.model.ContentItemDomain
import com.example.formnest.presentation.model.ContentItemUi
import com.example.formnest.shared.Mapper

class ContentUiMapper : Mapper<ContentItemDomain, ContentItemUi> {
    override fun map(input: ContentItemDomain): ContentItemUi {
        return when (input) {
            is ContentItemDomain.Page -> ContentItemUi.Page(
                title = input.title,
                items = input.items.map(::map)
            )

            is ContentItemDomain.Section -> ContentItemUi.Section(
                title = input.title,
                items = input.items.map(::map)
            )

            is ContentItemDomain.Text -> ContentItemUi.Text(title = input.title)
            is ContentItemDomain.Image -> ContentItemUi.Image(
                title = input.title,
                src = input.src
            )
        }
    }
}
