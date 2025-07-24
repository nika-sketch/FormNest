package com.example.formnest.presentation.hierarchy.mapper

import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.presentation.hierarchy.model.HierarchyContentUi
import com.example.formnest.shared.Mapper

class ContentUiMapper : Mapper<FormNestDomain, HierarchyContentUi> {
  override fun map(input: FormNestDomain): HierarchyContentUi {
    return when (input) {
      is FormNestDomain.Page -> HierarchyContentUi.Page(
        title = input.title,
        items = input.items.map(::map)
      )

      is FormNestDomain.Section -> HierarchyContentUi.Section(
        title = input.title,
        items = input.items.map(::map)
      )

      is FormNestDomain.Text -> HierarchyContentUi.Text(title = input.title)
      is FormNestDomain.Image -> HierarchyContentUi.Image(
        title = input.title,
        src = input.src
      )
    }
  }
}
