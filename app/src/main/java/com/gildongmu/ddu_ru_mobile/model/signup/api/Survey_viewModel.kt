package com.gildongmu.ddu_ru_mobile.model.signup.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.ddu_ru_mobile.model.signup.survey.Servey
import com.gildongmu.ddu_ru_mobile.model.signup.survey.SurveyElements
import com.gildongmu.ddu_ru_mobile.network.ApiClient
import kotlinx.coroutines.launch

class SurveyViewModel : ViewModel() {
    val nickName = MutableLiveData<String>()
    val surveyResult = MutableLiveData<Servey>(Servey())
    val selectedActivities: MutableSet<String> = mutableSetOf()

    val isLoading = MutableLiveData<Boolean>(false)
    val submitSuccess = MutableLiveData<Boolean>(false)
    val submitError = MutableLiveData<String?>(null)

    private  val surveyApiService = ApiClient.apiService

    fun setNickName(newNickName: String) { nickName.value = newNickName }

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

    private val interestMap = mapOf(
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

    fun toggleActivity(choiceKorean: String) {
        surveyResult.value = surveyResult.value?.apply {
            if (selectedActivities.contains(choiceKorean)) selectedActivities.remove(choiceKorean)
            else if (selectedActivities.size < 3) selectedActivities.add(choiceKorean)
        }
    }

    fun clearServeyList() { surveyResult.value = Servey() }

    fun finalizeInterests() {
        surveyResult.value = surveyResult.value?.apply {
            interests = if(selectedActivities.isNotEmpty()){
                selectedActivities.mapNotNull { interestMap[it] }.toSet()
            } else null
        }
    }
    private fun Servey.toRequest(nickName: String): TravelPreferenceRequest {
        val list = mutableListOf<String>()

        planStyle?.name?.let { list.add(it) }
        tastingStyle?.name?.let { list.add(it) }
        stayStyle?.name?.let { list.add(it) }
        expenseStyle?.name?.let { list.add(it) }
        moveStyle?.name?.let { list.add(it) }
        spendStyle?.name?.let { list.add(it) }
        captureStyle?.name?.let { list.add(it) }
        paceStyle?.name?.let { list.add(it) }

        interests?.forEach { list.add(it.name) }

        return TravelPreferenceRequest(
            surveyVersion = 1,                 // TODO: 버전 관리 필요하면 상수나 BuildConfig로 뺄 것
            nickName = nickName,
            preferencesList = if (list.isNotEmpty()) list else null
        )
    }

    fun submitSurveyToServer(userId: String = "tem") {
        viewModelScope.launch {
            try {
                isLoading.value = true
                submitError.value = null

                finalizeInterests()

                //Todo : 나중에 로그인 하면 기본 user정보로 바꾸기
                val request = surveyResult.value?.toRequest(nickName.value ?: "")
                    ?: throw IllegalStateException("설문데이터가 없습니다.")
                // API 호출
                val response = surveyApiService.sendTravelPreference(userId ,request)

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
