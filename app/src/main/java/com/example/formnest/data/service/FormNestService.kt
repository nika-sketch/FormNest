package com.example.formnest.data.service

import com.example.formnest.BuildConfig
import com.example.formnest.data.model.remote.FormNestApiModel
import retrofit2.Response
import retrofit2.http.GET

interface FormNestService {

  @GET(BuildConfig.FORM_NEST_ENDPOING)
  suspend fun fetchSurveyData(): Response<FormNestApiModel>
}
