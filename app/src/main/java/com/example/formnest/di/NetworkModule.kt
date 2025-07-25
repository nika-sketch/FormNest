package com.example.formnest.di

import com.example.formnest.BuildConfig
import com.example.formnest.data.service.FormNestService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface NetworkModule {

  fun provideFormNestService(): FormNestService

  class NetworkModuleImpl : NetworkModule {

    override fun provideFormNestService(): FormNestService =
      provideRetrofitClient(okHttpClient).create(FormNestService::class.java)

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(
      HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
      }).callTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(15, TimeUnit.SECONDS)
      .readTimeout(15, TimeUnit.SECONDS)
      .connectTimeout(15, TimeUnit.SECONDS)
      .retryOnConnectionFailure(true).build()

    private fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
      val moshiConverterFactory = MoshiConverterFactory.create(
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
      )
      return Retrofit.Builder().baseUrl(BuildConfig.FORM_NEST_BASE_URL)
        .addConverterFactory(moshiConverterFactory)
        .client(okHttpClient)
        .build()
    }
  }
}
