package com.example.formnest.data.mapper

import com.example.formnest.data.cache.FormNestItemTypeEntity
import com.example.formnest.data.model.remote.FormNestItemType

fun FormNestItemType.toEntity(): FormNestItemTypeEntity = when (this) {
  FormNestItemType.PAGE -> FormNestItemTypeEntity.PAGE
  FormNestItemType.SECTION -> FormNestItemTypeEntity.SECTION
  FormNestItemType.TEXT -> FormNestItemTypeEntity.TEXT
  FormNestItemType.IMAGE -> FormNestItemTypeEntity.IMAGE
}
