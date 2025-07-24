package com.example.formnest.data.local

import com.example.formnest.data.model.FormNestApiModel
import com.example.formnest.data.model.FormNestItemType
import com.example.formnest.domain.model.FormNestDomain

fun FormNestEntity.toDomain(): FormNestDomain? {
  return when (type) {
    FormNestItemTypeEntity.PAGE -> FormNestDomain.Page(
      title = title.orEmpty(),
      items = items?.mapNotNull { it.toDomain() }.orEmpty()
    ).takeIf { title != null }

    FormNestItemTypeEntity.SECTION -> FormNestDomain.Section(
      title = title.orEmpty(),
      items = items?.mapNotNull { it.toDomain() }.orEmpty()
    ).takeIf { title != null }

    FormNestItemTypeEntity.TEXT -> title?.let { FormNestDomain.Text(it) }
    FormNestItemTypeEntity.IMAGE -> if (title != null && src != null)
      FormNestDomain.Image(title, src) else null
  }
}

fun FormNestApiModel.toEntity(): FormNestEntity = FormNestEntity(
  type = type.toEntity(),
  title = title,
  src = src,
  items = items?.map {
    it.toEntity()
  }
)

fun FormNestItemType.toEntity(): FormNestItemTypeEntity = when (this) {
  FormNestItemType.PAGE -> FormNestItemTypeEntity.PAGE
  FormNestItemType.SECTION -> FormNestItemTypeEntity.SECTION
  FormNestItemType.TEXT -> FormNestItemTypeEntity.TEXT
  FormNestItemType.IMAGE -> FormNestItemTypeEntity.IMAGE
}