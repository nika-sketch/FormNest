package com.example.formnest.data.model.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.formnest.data.cache.FormNestItemTypeEntity

@Entity(tableName = "form_nest")
data class FormNestEntity(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val type: FormNestItemTypeEntity,
  val title: String?,
  val src: String?,
  val items: List<FormNestEntity>? = null
)