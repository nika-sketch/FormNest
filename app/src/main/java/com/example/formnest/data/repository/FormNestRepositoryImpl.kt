package com.example.formnest.data.repository

import com.example.formnest.data.local.FormNestDao
import com.example.formnest.data.local.toDomain
import com.example.formnest.data.local.toEntity
import com.example.formnest.data.mapper.toDomain
import com.example.formnest.data.service.FormNestService
import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.shared.DispatcherProvider
import kotlinx.coroutines.withContext

class FormNestRepositoryImpl(
  private val service: FormNestService,
  private val dao: FormNestDao,
  private val dispatcherProvider: DispatcherProvider,
) : FormNestRepository {

  override suspend fun surveyData(): Result<FormNestDomain> = withContext(dispatcherProvider.io()) {
    val cachedResult = dao.getAll()
    if (cachedResult.isNotEmpty()) {
      Result.success(cachedResult.first().toDomain() ?: error("Cached data is invalid"))
    } else {
      val response = service.fetchSurveyData()
      if (response.isSuccessful) {
        val body = response.body() ?: throw IllegalStateException("Body is null")
        val domain = body.toDomain() ?: throw IllegalStateException("Invalid data structure")
        dao.clear()
        dao.insertAll(listOf(body.toEntity()))
        Result.success(domain)
      } else {
        throw Exception("Network call failed with code: ${response.code()}")
      }
    }
  }
}
