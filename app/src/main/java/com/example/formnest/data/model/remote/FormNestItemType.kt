package com.example.formnest.data.model.remote

import com.squareup.moshi.Json

enum class FormNestItemType {
  @Json(name = "page")
  PAGE,

  @Json(name = "section")
  SECTION,

  @Json(name = "text")
  TEXT,

  @Json(name = "image")
  IMAGE
}