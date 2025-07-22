package com.example.formnest

import android.app.Application
import com.example.formnest.data.repository.FormNestRepositoryImpl
import com.example.formnest.di.NetworkModule
import com.example.formnest.domain.repository.FormNestRepository

class FormNestApp : Application() {

    companion object {
        lateinit var formNestRepository: FormNestRepository
    }

    override fun onCreate() {
        super.onCreate()
        formNestRepository = FormNestRepositoryImpl(
            NetworkModule.NetworkModuleImpl().provideFormNestService()
        )
    }
}
