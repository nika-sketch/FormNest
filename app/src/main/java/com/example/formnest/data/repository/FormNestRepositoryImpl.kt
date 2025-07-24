package com.example.formnest.data.repository

import com.example.formnest.data.cache.FormNestDao
import com.example.formnest.data.mapper.toFormNestDomain
import com.example.formnest.data.mapper.toEntity
import com.example.formnest.data.service.FormNestService
import com.example.formnest.domain.model.FormNestDomain
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.shared.CheckNetwork
import com.example.formnest.shared.DispatcherProvider
import kotlinx.coroutines.withContext

class FormNestRepositoryImpl(
  private val formNestService: FormNestService,
  private val formNestDao: FormNestDao,
  private val dispatcherProvider: DispatcherProvider,
  private val checkNetwork: CheckNetwork
) : FormNestRepository {

  override suspend fun surveyData(): Result<FormNestDomain> = withContext(dispatcherProvider.io()) {
    val cachedResult = formNestDao.formNestList()
    if (!checkNetwork.isConnected() && cachedResult.isEmpty()) {
      return@withContext Result.failure(IllegalStateException("No network connection"))
    }
    if (cachedResult.isNotEmpty()) {
      Result.success(
        cachedResult.first().toFormNestDomain()
          ?: return@withContext Result.failure(IllegalStateException("Cached data is invalid"))
      )
    } else {
      runCatching {
        val response = formNestService.fetchSurveyData()
        if (!response.isSuccessful) error("Network call failed with code: ${response.code()}")
        val body = response.body() ?: error("Body is null")
        val formNestDomain = body.toFormNestDomain() ?: error("Invalid data structure")
        formNestDao.clearFormNestList()
        formNestDao.insertFormNestList(listOf(body.toEntity()))
        formNestDomain
      }.fold(
        onSuccess = { Result.success(it) },
        onFailure = { error -> Result.failure(error) }
      )
    }
  }
}
