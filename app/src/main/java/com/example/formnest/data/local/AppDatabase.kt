package com.example.formnest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FormNestEntity::class], version = 1)
@TypeConverters(FormNestTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

  abstract fun formNestDao(): FormNestDao
}
