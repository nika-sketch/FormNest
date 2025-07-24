package com.example.formnest.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FormNestDao {
  @Query("SELECT * FROM form_nest")
  suspend fun getAll(): List<FormNestEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(entities: List<FormNestEntity>)

  @Query("DELETE FROM form_nest")
  suspend fun clear()
}
