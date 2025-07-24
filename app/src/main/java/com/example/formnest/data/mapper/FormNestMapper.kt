package com.example.formnest.data.mapper

import com.example.formnest.data.model.FormNestApiModel
import com.example.formnest.data.model.FormNestItemType
import com.example.formnest.domain.model.FormNestDomain
import kotlin.collections.orEmpty

fun FormNestApiModel.toDomain(): FormNestDomain? {
  return when (type) {
    FormNestItemType.PAGE -> FormNestDomain.Page(
      title = title.orEmpty(),
      items = items?.mapNotNull(FormNestApiModel::toDomain).orEmpty()
    ).takeIf { title != null }

    FormNestItemType.SECTION -> FormNestDomain.Section(
      title = title.orEmpty(),
      items = items?.mapNotNull(FormNestApiModel::toDomain).orEmpty()
    ).takeIf { title != null }

    FormNestItemType.TEXT -> title?.let(FormNestDomain::Text)
    FormNestItemType.IMAGE -> {
      if (title != null && src != null) {
        FormNestDomain.Image(title, src)
      } else null
    }
  }
}
