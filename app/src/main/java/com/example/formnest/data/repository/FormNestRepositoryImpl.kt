package com.example.formnest.data.repository

import com.example.formnest.data.mapper.toDomain
import com.example.formnest.data.service.FormNestService
import com.example.formnest.domain.model.ContentItemDomain
import com.example.formnest.domain.repository.FormNestRepository
import com.example.formnest.shared.runCatchingCancellable

class FormNestRepositoryImpl(
    private val service: FormNestService
) : FormNestRepository {

    override suspend fun surveyData(): Result<ContentItemDomain> = runCatchingCancellable {
        val response = service.fetchSurveyData()
        if (response.isSuccessful) {
            response.body()?.toDomain()
                ?: throw IllegalStateException("Response body is null or invalid")
        } else {
            throw Exception("Network call failed with code: ${response.code()}")
        }
    }
}
