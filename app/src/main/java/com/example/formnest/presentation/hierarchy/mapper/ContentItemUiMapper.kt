package com.example.formnest.presentation.hierarchy.mapper

import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.presentation.hierarchy.model.ContentItemUi
import com.example.formnest.shared.Mapper

class ContentUiMapper : Mapper<FormNestDomain, ContentItemUi> {
    override fun map(input: FormNestDomain): ContentItemUi {
        return when (input) {
            is FormNestDomain.Page -> ContentItemUi.Page(
                title = input.title,
                items = input.items.map(::map)
            )

            is FormNestDomain.Section -> ContentItemUi.Section(
                title = input.title,
                items = input.items.map(::map)
            )

            is FormNestDomain.Text -> ContentItemUi.Text(title = input.title)
            is FormNestDomain.Image -> ContentItemUi.Image(
                title = input.title,
                src = input.src
            )
        }
    }
}
