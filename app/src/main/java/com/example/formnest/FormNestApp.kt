package com.example.formnest

import android.app.Application
import com.example.formnest.di.NetworkModule

class FormNestApp : Application() {

    companion object {
        lateinit var networkModule: NetworkModule
    }

    override fun onCreate() {
        super.onCreate()
        networkModule = NetworkModule.NetworkModuleImpl()
    }
}
