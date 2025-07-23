package com.example.formnest.domain.repository

import com.example.formnest.domain.model.FormNestDomain

interface FormNestRepository {

    suspend fun surveyData(): Result<FormNestDomain>
}
