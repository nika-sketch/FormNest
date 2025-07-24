package com.example.formnest.data.cache

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.formnest.data.model.cache.FormNestEntity

@Dao
interface FormNestDao {
  @Query("SELECT * FROM form_nest")
  suspend fun formNestList(): List<FormNestEntity>

  @Upsert
  suspend fun insertFormNestList(entities: List<FormNestEntity>)

  @Query("DELETE FROM form_nest")
  suspend fun clearFormNestList()
}
