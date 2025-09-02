package com.gildongmu.ddu_ru_mobile.network


import com.gildongmu.ddu_ru_mobile.model.signup.api.TravelPreferenceRequest
import com.gildongmu.ddu_ru_mobile.model.signup.api.TravelPreferenceResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface SurveyApiService {

    @POST(ApiPath.POST_TRAVEL_PREFERENCES)
    suspend fun sendTravelPreference(
        // int일지 string일지는 모르겠음
        @Path("userId") userId: String,
        @Body request: TravelPreferenceRequest
    ): Response<TravelPreferenceResponse>
}