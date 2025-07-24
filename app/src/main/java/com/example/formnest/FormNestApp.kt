package com.example.formnest

import android.app.Application
import androidx.room.Room
import com.example.formnest.data.local.AppDatabase
import com.example.formnest.data.repository.FormNestRepositoryImpl
import com.example.formnest.di.NetworkModule
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.shared.DispatcherProvider

class FormNestApp : Application() {

  companion object {
    lateinit var formNestRepository: FormNestRepository
  }

  override fun onCreate() {
    super.onCreate()
    formNestRepository = FormNestRepositoryImpl(
      service = NetworkModule.NetworkModuleImpl().provideFormNestService(),
      dao = Room.databaseBuilder(
        this,
        AppDatabase::class.java,
        "formnest.db"
      ).build().formNestDao(),
      dispatcherProvider = DispatcherProvider.Default(),
    )
  }
}
