package com.example.formnest.data.repository

import com.example.formnest.data.mapper.toDomain
import com.example.formnest.data.service.FormNestService
import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.shared.DispatcherProvider
import com.example.formnest.shared.runCatchingCancellable
import kotlinx.coroutines.withContext

class FormNestRepositoryImpl(
  private val service: FormNestService,
  private val dispatcherProvider: DispatcherProvider,
) : FormNestRepository {

  override suspend fun surveyData(): Result<FormNestDomain> = runCatchingCancellable {
    withContext(dispatcherProvider.io()) {
      val response = service.fetchSurveyData()
      if (response.isSuccessful) {
        response.body()?.toDomain()
          ?: throw IllegalStateException("Response body is null or invalid")
      } else {
        throw Exception("Network call failed with code: ${response.code()}")
      }
    }
  }
}
