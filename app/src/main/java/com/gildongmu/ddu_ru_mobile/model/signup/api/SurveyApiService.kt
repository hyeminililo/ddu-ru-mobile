package com.gildongmu.ddu_ru_mobile.model.signup.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SurveyApiService {
    @POST("/api/v1/survey")
    suspend fun submitSurvey(@Body surveyData: Map<String, String?>): Response<SurveyResponse>
}

data class SurveyResponse(val success: Boolean, val message: String, val data: SurveyData? = null)

data class SurveyData(val surveyId: String, val submittedAt: String)
