package com.gildongmu.ddu_ru_mobile.ui.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gildongmu.ddu_ru_mobile.R
import com.gildongmu.ddu_ru_mobile.model.signup.survey.Survey
import com.gildongmu.ddu_ru_mobile.model.signup.survey.SurveyList

class SurveyActivity : AppCompatActivity() {

    private var currentIndex = 0 // 현재 설문 항목의 인덱스
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnChoice1: Button
    private lateinit var btnChoice2: Button
    private lateinit var tvQuestion: TextView
    private lateinit var btnPrev: ImageView
    private lateinit var btnComplete: Button
    private val handler = Handler(Looper.getMainLooper())

    // list_survey.xml의 버튼들
    private lateinit var btnSightseeing: Button
    private lateinit var btnExhibition: Button
    private lateinit var btnNature: Button
    private lateinit var btnFoodTour: Button
    private lateinit var btnShopping: Button
    private lateinit var btnRelaxation: Button
    private lateinit var btnActivity: Button
    private lateinit var btnAmusementPark: Button
    private lateinit var btnFestival: Button

    // 선택된 여행 활동들을 저장
    private val selectedTravelActivities = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        initializeViews()

        val surveyList = SurveyList().surveyList
        progressBar.progressTintList =
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.mainColor))

        // 첫 번째 설문 데이터를 표시
        updateSurvey(surveyList[currentIndex])

        // 이전 버튼 클릭 시
        btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                updateSurvey(surveyList[currentIndex])
            }
        }

        // 선택 버튼 클릭 시
        btnChoice1.setOnClickListener {
            setSelectedButtonStyle(btnChoice1) // 선택된 버튼 스타일 변경
            // 0.5초 후에 다음 설문으로 이동 (색상 변경이 보이도록)
            handler.postDelayed({ handleChoice(0) }, 500)
        }

        btnChoice2.setOnClickListener {
            setSelectedButtonStyle(btnChoice2) // 선택된 버튼 스타일 변경
            // 0.5초 후에 다음 설문으로 이동 (색상 변경이 보이도록)
            handler.postDelayed({ handleChoice(1) }, 500)
        }
    }

    private fun initializeViews() {
        tvQuestion = findViewById(R.id.tvQuestion)
        btnChoice1 = findViewById(R.id.btnChoice1)
        btnChoice2 = findViewById(R.id.btnChoice2)
        btnPrev = findViewById(R.id.btnPrev)
        btnComplete = findViewById(R.id.btnComplete)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBarMain)

        // list_survey.xml의 버튼들 초기화
        btnSightseeing = findViewById(R.id.btnSightseeing)
        btnExhibition = findViewById(R.id.btnExhibition)
        btnNature = findViewById(R.id.btnNature)
        btnFoodTour = findViewById(R.id.btnFoodTour)
        btnShopping = findViewById(R.id.btnShopping)
        btnRelaxation = findViewById(R.id.btnRelaxation)
        btnActivity = findViewById(R.id.btnActivity)
        btnAmusementPark = findViewById(R.id.btnAmusementPark)
        btnFestival = findViewById(R.id.btnFestival)

        // RecyclerView 초기 설정 - 3열 그리드로 설정
        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)
        recyclerView.visibility = View.GONE // 초기에는 숨김

        // 완료 버튼 클릭 리스너 설정
        btnComplete.setOnClickListener { submitSurveyToServer() }
    }

    private fun handleChoice(choiceIndex: Int) {
        val surveyList = SurveyList().surveyList
        //        if (currentIndex < surveyList.size - 1) {
        if (currentIndex < surveyList.size) {
            currentIndex++
            updateSurvey(surveyList[currentIndex])
        } else {
            // 마지막 설문 완료
            // TODO: 결과 처리
        }
    }

    // 설문 항목 업데이트 함수
    private fun updateSurvey(surveyItem: Survey) {

        tvQuestion.text = surveyItem.question

        if (surveyItem.options.size > 2) {
            // 마지막 설문 (여행 활동 선택) - RecyclerView 사용
            android.util.Log.d("SurveyActivity", "마지막 설문 (여행 활동 선택) - RecyclerView 표시")
            showRecyclerView(surveyItem.options)
            hideChoiceButtons()
            showCompleteButton() // 완료 버튼 표시
        } else {
            // 일반 설문 - 기존 버튼 사용
            android.util.Log.d("SurveyActivity", "일반 설문 - 2개 선택지 버튼 표시")
            showChoiceButtons()
            hideRecyclerView()
            showPrevButton() // 뒤로 버튼 표시
            btnChoice1.text = surveyItem.options[0]
            btnChoice2.text = surveyItem.options[1]

            // 버튼 스타일 초기화 (기본 회색) - 새로운 설문 시작 시에만
            resetButtonStyles()
        }

        // 진행률 업데이트
        updateProgress()
    }

    private fun showRecyclerView(options: List<String>) {
        android.util.Log.d("SurveyActivity", "showRecyclerView 호출됨, options: $options")

        try {
            recyclerView.visibility = View.VISIBLE
            selectedTravelActivities.clear() // 선택 초기화

            // SurveyOptionAdapter를 사용하여 RecyclerView 설정
            val adapter =
                    SurveyOptionAdapter(options) { option ->
                        // 옵션 선택 시 처리
                        handleTravelActivitySelection(option)
                    }

            recyclerView.adapter = adapter
            // 3열 그리드 레이아웃 설정
            recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 3)
            android.util.Log.d("SurveyActivity", "RecyclerView 어댑터 설정 완료 (3열 그리드)")
        } catch (e: Exception) {
            android.util.Log.e("SurveyActivity", "showRecyclerView 오류: ${e.message}", e)
        }
    }

    /** 여행 활동 선택 처리 */
    private fun handleTravelActivitySelection(option: String) {
        android.util.Log.d("SurveyActivity", "handleTravelActivitySelection 호출: $option")
        android.util.Log.d("SurveyActivity", "현재 선택된 활동들: $selectedTravelActivities")

        if (selectedTravelActivities.contains(option)) {
            // 이미 선택된 경우: 선택 해제
            selectedTravelActivities.remove(option)
            android.util.Log.d(
                    "SurveyActivity",
                    "선택 해제: $option (현재 ${selectedTravelActivities.size}개)"
            )

            // progress 복구 (3개 미만이 되면)
            updateProgressForLastSurvey()
        } else {
            // 선택되지 않은 경우: 최대 3개까지만 선택 가능
            if (selectedTravelActivities.size < 3) {
                selectedTravelActivities.add(option)
                android.util.Log.d(
                        "SurveyActivity",
                        "선택 추가: $option (현재 ${selectedTravelActivities.size}/3)"
                )

                // 3개 선택 완료 시 progress 100%로 설정
                if (selectedTravelActivities.isNotEmpty() ) {
                    android.util.Log.d(
                            "SurveyActivity",
                            "3개 선택 완료! 선택된 활동: $selectedTravelActivities"
                    )
                    // progress를 100%로 설정
                    progressBar.progress = 100
                    android.util.Log.d("SurveyActivity", "Progress 100% 완성!")

                    // 완료 버튼 활성화 (사용자가 직접 클릭하도록)
                    updateCompleteButtonState()
                    android.util.Log.d("SurveyActivity", "3개 선택 완료! 이제 완료 버튼을 눌러주세요!")
                } else {
                    // 3개 미만일 때는 일반 progress 계산
                    updateProgressForLastSurvey()
                }
            } else {
                android.util.Log.d("SurveyActivity", "이미 3개를 선택했습니다. 더 이상 선택할 수 없습니다.")
                // TODO: 사용자에게 알림 표시
            }
        }

        // 선택 상태 변경 후 완료 버튼 상태 업데이트
        android.util.Log.d(
                "SurveyActivity",
                "updateCompleteButtonState() 호출 전 - 선택된 개수: ${selectedTravelActivities.size}"
        )
        updateCompleteButtonState()
        android.util.Log.d(
                "SurveyActivity",
                "updateCompleteButtonState() 호출 후 - 완료 버튼 활성화: ${btnComplete.isEnabled}"
        )
    }

    private fun hideRecyclerView() {
        recyclerView.visibility = View.GONE
    }

    private fun showChoiceButtons() {
        btnChoice1.visibility = View.VISIBLE
        btnChoice2.visibility = View.VISIBLE
    }

    private fun hideChoiceButtons() {
        btnChoice1.visibility = View.GONE
        btnChoice2.visibility = View.GONE
    }

    private fun showPrevButton() {
        btnPrev.visibility = View.VISIBLE
        btnComplete.visibility = View.GONE
    }

    private fun showCompleteButton() {
        btnPrev.visibility = View.VISIBLE // 이전 버튼은 계속 표시 (왼쪽 위)
        btnComplete.visibility = View.VISIBLE // 완료 버튼도 표시 (하단)
        updateCompleteButtonState() // 완료 버튼 상태 업데이트
    }

    private fun updateProgress() {
        val surveyList = SurveyList().surveyList
        // 진행률 계산 수정: 0~100% 범위로 표시
        val progress = ((currentIndex) * 100) / surveyList.size
        progressBar.progress = progress

        // 디버깅: 진행률 계산 확인
        android.util.Log.d(
                "SurveyActivity",
                "진행률 업데이트: currentIndex=$currentIndex, progress=$progress%, total=${surveyList.size}"
        )
    }

    /** 마지막 설문에서의 progress 업데이트 (선택 개수에 따라) */
    private fun updateProgressForLastSurvey() {
        val surveyList = SurveyList().surveyList
        val baseProgress = ((currentIndex) * 100) / surveyList.size // 기본 progress

        // 선택된 개수에 따라 추가 progress 계산
        val selectionProgress = (selectedTravelActivities.size * 100) / 3 // 0%, 33%, 66%
        val totalProgress = baseProgress + selectionProgress

        progressBar.progress = totalProgress

        android.util.Log.d(
                "SurveyActivity",
                "마지막 설문 Progress: 기본=$baseProgress%, 선택=$selectionProgress%, 총=$totalProgress% (${selectedTravelActivities.size}/3)"
        )
    }

    /** 완료 버튼의 활성화/비활성화 상태 업데이트 */
    private fun updateCompleteButtonState() {
        android.util.Log.d(
                "SurveyActivity",
                "updateCompleteButtonState() 시작 - 선택된 개수: ${selectedTravelActivities.size}"
        )

        if (selectedTravelActivities.isEmpty()) {
            // 선택된 것이 없으면 비활성화
            btnComplete.isEnabled = false
            btnComplete.background = getDrawable(R.drawable.button_complete_disabled)
            btnComplete.setTextColor(getColor(android.R.color.darker_gray))
            android.util.Log.d("SurveyActivity", "완료 버튼 비활성화 (선택 없음) - 회색 배경")
        } else {
            // 선택된 것이 있으면 활성화 (주황색 배경)
            btnComplete.isEnabled = true
            btnComplete.backgroundTintList =
                    getColorStateList(R.color.mainColor) // mainColor 주황색 사용
            btnComplete.setTextColor(getColor(android.R.color.white))
            android.util.Log.d(
                    "SurveyActivity",
                    "완료 버튼 활성화 (${selectedTravelActivities.size}개 선택) - 주황색"
            )
        }

        android.util.Log.d(
                "SurveyActivity",
                "updateCompleteButtonState() 완료 - 버튼 활성화: ${btnComplete.isEnabled}"
        )
    }

    /** 버튼 스타일을 기본 상태(회색)로 초기화 */
    private fun resetButtonStyles() {
        btnChoice1.backgroundTintList = getColorStateList(android.R.color.darker_gray)
        btnChoice1.setTextColor(getColor(android.R.color.black))
        btnChoice2.backgroundTintList = getColorStateList(android.R.color.darker_gray)
        btnChoice2.setTextColor(getColor(android.R.color.black))

        // 디버깅: 스타일 초기화 확인
        android.util.Log.d("SurveyActivity", "버튼 스타일 초기화: 모든 버튼을 회색으로")
    }

    /** 선택된 버튼을 주황색으로 변경 */
    private fun setSelectedButtonStyle(selectedButton: Button) {
        // 선택된 버튼을 주황색으로
        selectedButton.backgroundTintList = getColorStateList(R.color.mainColor)
        selectedButton.setTextColor(getColor(android.R.color.white))

        // 선택되지 않은 버튼을 회색으로
        val unselectedButton = if (selectedButton == btnChoice1) btnChoice2 else btnChoice1
        unselectedButton.backgroundTintList = getColorStateList(android.R.color.darker_gray)
        unselectedButton.setTextColor(getColor(android.R.color.black))

        // 디버깅: 색상 변경 확인
        android.util.Log.d("SurveyActivity", "버튼 색상 변경: ${selectedButton.text} -> 주황색")
    }

    /** 설문 결과를 서버에 전송 */
    private fun submitSurveyToServer() {
        if (selectedTravelActivities.size < 3) {
            android.util.Log.d("SurveyActivity", "3개를 모두 선택해주세요!")
            // TODO: 사용자에게 알림 표시
            return
        }

        android.util.Log.d("SurveyActivity", "서버에 설문 결과 전송 시작...")
        android.util.Log.d("SurveyActivity", "선택된 여행 활동: $selectedTravelActivities")

        // TODO: 실제 서버 API 호출
        // 1. 사용자 닉네임
        // 2. 설문 응답들
        // 3. 선택된 여행 활동들

        // 임시로 성공 처리
        android.util.Log.d("SurveyActivity", "설문 제출 완료!")
        // TODO: 결과 화면으로 이동
    }
}
