package com.gildongmu.ddu_ru_mobile.model.signup.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.ddu_ru_mobile.BuildConfig
import com.gildongmu.ddu_ru_mobile.model.signup.survey.Servey
import com.gildongmu.ddu_ru_mobile.model.signup.survey.SurveyElements
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SurveyViewModel : ViewModel() {
    val currentIndex = MutableLiveData(0)
    val nickName = MutableLiveData<String>()

    // Todo : 이렇게 하면 suerveyList의 값이 아니라 survey에 Null이 들어가는건지 확인해야할듯
    val surveyResult = MutableLiveData<Servey>(Servey())
    val selectedActivities: MutableSet<String> = mutableSetOf()
    
    // API 상태 관리
    val isLoading = MutableLiveData<Boolean>(false)
    val submitSuccess = MutableLiveData<Boolean>(false)
    val submitError = MutableLiveData<String?>(null)

    // Retrofit 설정
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val surveyApiService = retrofit.create(SurveyApiService::class.java)

    fun setNickName(newNickName: String) {
        nickName.value = newNickName
    }

    fun setSingleOption(choiceIndex: Int, surveyItem: SurveyElements) {
        surveyResult.value = surveyResult.value?.apply {
            when (surveyItem.surveyId) {
                1 -> planStyle = if (choiceIndex == 0) Servey.PlanStyle.PLANNER else Servey.PlanStyle.FREE

                2 -> tastingStyle = if (choiceIndex == 0) Servey.TastingStyle.WAIT else Servey.TastingStyle.NEARBY

                3 -> stayStyle = if (choiceIndex == 0) Servey.StayStyle.HOTEL else Servey.StayStyle.JUST_SLEEP

                4 -> expenseStyle = if (choiceIndex == 0) Servey.ExpenseStyle.EACH_PAYS else Servey.ExpenseStyle.POOLED

                5 -> moveStyle = if (choiceIndex == 0) Servey.MoveStyle.WALK_BUS else Servey.MoveStyle.TAXI

                6 -> spendStyle = if (choiceIndex == 0) Servey.SpendStyle.SPLURGE else Servey.SpendStyle.SAVER

                7 -> captureStyle = if (choiceIndex == 0) Servey.CaptureStyle.PHOTO else Servey.CaptureStyle.EYES

                8 -> paceStyle = if (choiceIndex == 0) Servey.PaceStyle.EARLY_FULL else Servey.PaceStyle.RELAXED
            }
        }
    }

    private val interestMap = mapOf( // CHANGED
        "관광" to Servey.Interest.SIGHTSEEING,
        "관람" to Servey.Interest.EXHIBITION,
        "자연 탐방" to Servey.Interest.NATURE,
        "먹방" to Servey.Interest.FOOD,
        "쇼핑" to Servey.Interest.SHOPPING,
        "휴양" to Servey.Interest.RESORT,
        "액티비티" to Servey.Interest.ACTIVITY,
        "놀이공원" to Servey.Interest.THEME_PARK,
        "페스티벌" to Servey.Interest.FESTIVAL
    )

    fun toggleActivity(choiceKorean: String, surveyItem: SurveyElements) {
        if (surveyItem.surveyId != 9) return
        surveyResult.value = surveyResult.value?.apply {
            if (selectedActivities.contains(choiceKorean)) selectedActivities.remove(choiceKorean)
            else if (selectedActivities.size < 3) selectedActivities.add(choiceKorean)
        }

    }

    fun submitSurvey() {

    }


    fun <T> setSurveyOption(update: Servey.() -> Unit) {
        surveyResult.value = surveyResult.value?.apply(update)
    }

    fun clearServeyList() {
        surveyResult.value = Servey()
    }
    fun finalizeInterests() {
        surveyResult.value = surveyResult.value?.apply {
            interests = if(selectedActivities.isNotEmpty()){
                selectedActivities.mapNotNull { interestMap[it] }.toSet()
            } else null
        }
    }

    fun submitSurveyToServer() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                submitError.value = null
                
                // interests 최종화
                finalizeInterests()
                
                // 서버 전송 데이터 준비
                val surveyData = surveyResult.value?.toServerMap()
                    ?: throw Exception("설문 데이터가 없습니다")
                
                // API 호출
                val response = surveyApiService.submitSurvey(surveyData)
                
                if (response.isSuccessful) {
                    submitSuccess.value = true
                } else {
                    submitError.value = "서버 오류: ${response.code()}"
                }
            } catch (e: Exception) {
                submitError.value = "전송 실패: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }


}
