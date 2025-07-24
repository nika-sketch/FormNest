package com.example.formnest.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.formnest.data.model.cache.FormNestEntity

@Database(entities = [FormNestEntity::class], version = 1)
@TypeConverters(FormNestTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun formNestDao(): FormNestDao
}
