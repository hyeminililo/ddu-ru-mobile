package com.gildongmu.ddu_ru_mobile.network

import com.gildongmu.ddu_ru_mobile.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService:  SurveyApiService = retrofit.create(SurveyApiService::class.java)
}