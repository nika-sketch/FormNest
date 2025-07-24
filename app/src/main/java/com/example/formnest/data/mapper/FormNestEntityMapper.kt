package com.example.formnest.data.mapper

import com.example.formnest.data.local.FormNestEntity
import com.example.formnest.data.model.FormNestApiModel

fun FormNestApiModel.toEntity(): FormNestEntity = FormNestEntity(
  type = type.toEntity(),
  title = title,
  src = src,
  items = items?.map { it.toEntity() }
)
