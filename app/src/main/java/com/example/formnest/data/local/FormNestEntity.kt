package com.example.formnest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form_nest")
data class FormNestEntity(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val type: FormNestItemTypeEntity,
  val title: String?,
  val src: String?,
  val items: List<FormNestEntity>? = null
)
