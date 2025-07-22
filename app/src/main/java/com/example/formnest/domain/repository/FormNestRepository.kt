package com.example.formnest.domain.repository

import com.example.formnest.domain.model.ContentItemDomain

interface FormNestRepository {

    suspend fun surveyData(): Result<ContentItemDomain>
}
