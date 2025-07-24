package com.example.formnest.data.model.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FormNestApiModel(
  val type: FormNestItemType,
  val title: String?,
  val src: String?,
  val items: List<FormNestApiModel>? = null
)