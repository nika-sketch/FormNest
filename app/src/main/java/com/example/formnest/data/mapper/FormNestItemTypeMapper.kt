package com.example.formnest.data.mapper

import com.example.formnest.data.local.FormNestItemTypeEntity
import com.example.formnest.data.model.FormNestItemType

fun FormNestItemType.toEntity(): FormNestItemTypeEntity = when (this) {
  FormNestItemType.PAGE -> FormNestItemTypeEntity.PAGE
  FormNestItemType.SECTION -> FormNestItemTypeEntity.SECTION
  FormNestItemType.TEXT -> FormNestItemTypeEntity.TEXT
  FormNestItemType.IMAGE -> FormNestItemTypeEntity.IMAGE
}
