package com.example.formnest

import android.app.Application
import androidx.room.Room
import com.example.formnest.data.cache.AppDatabase
import com.example.formnest.data.repository.FormNestRepositoryImpl
import com.example.formnest.di.NetworkModule
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.shared.ConnectivityObserver
import com.example.formnest.shared.DispatcherProvider

class FormNestApp : Application() {

  companion object {
    lateinit var formNestRepository: FormNestRepository
    private const val FORM_NEST_DB = "formnest.db"
  }

  override fun onCreate() {
    super.onCreate()
    formNestRepository = FormNestRepositoryImpl(
      formNestService = NetworkModule.NetworkModuleImpl().provideFormNestService(),
      formNestDao = Room.databaseBuilder(
        context = this,
        klass = AppDatabase::class.java,
        name = FORM_NEST_DB
      ).build().formNestDao(),
      dispatcherProvider = DispatcherProvider.Default(),
      connectivityObserver = ConnectivityObserver.AndroidConnectivityObserver(context = this)
    )
  }
}
