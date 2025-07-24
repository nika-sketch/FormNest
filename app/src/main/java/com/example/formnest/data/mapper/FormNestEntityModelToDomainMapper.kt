package com.example.formnest.data.mapper

import com.example.formnest.data.local.FormNestEntity
import com.example.formnest.data.local.FormNestItemTypeEntity
import com.example.formnest.domain.model.FormNestDomain

fun FormNestEntity.toFormNestDomain(): FormNestDomain? {
  return when (type) {
    FormNestItemTypeEntity.PAGE -> FormNestDomain.Page(
      title = title.orEmpty(),
      items = items?.mapNotNull { it.toFormNestDomain() }.orEmpty()
    ).takeIf { title != null }

    FormNestItemTypeEntity.SECTION -> FormNestDomain.Section(
      title = title.orEmpty(),
      items = items?.mapNotNull { it.toFormNestDomain() }.orEmpty()
    ).takeIf { title != null }

    FormNestItemTypeEntity.TEXT -> title?.let { FormNestDomain.Text(it) }
    FormNestItemTypeEntity.IMAGE -> if (title != null && src != null)
      FormNestDomain.Image(title, src) else null
  }
}

