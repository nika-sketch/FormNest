package com.example.formnest.data.local

import androidx.room.TypeConverter
import com.example.formnest.data.model.FormNestItemType
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class FormNestTypeConverters {
  private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
  private val listType = Types.newParameterizedType(List::class.java, FormNestEntity::class.java)
  private val adapter = moshi.adapter<List<FormNestEntity>>(listType)

  @TypeConverter
  fun fromEntityList(value: List<FormNestEntity>?): String? = value?.let { adapter.toJson(it) }

  @TypeConverter
  fun toEntityList(json: String?): List<FormNestEntity>? = json?.let { adapter.fromJson(it) }

  @TypeConverter
  fun fromType(value: FormNestItemType): String = value.name

  @TypeConverter
  fun toType(value: String): FormNestItemType = FormNestItemType.valueOf(value)
}
