package com.gildongmu.ddu_ru_mobile.ui.signup

import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
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
    private lateinit var textViewDownload: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnChoice1: Button
    private lateinit var btnChoice2: Button
    private lateinit var tvQuestion: TextView
    private lateinit var btnPrev: Button
    private val handler = Handler(Looper.getMainLooper())

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
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBarMain)
        textViewDownload = findViewById(R.id.textViewDownload)

        // RecyclerView 초기 설정
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView.visibility = View.GONE // 초기에는 숨김
    }

    private fun handleChoice(choiceIndex: Int) {
        val surveyList = SurveyList().surveyList
//        if (currentIndex < surveyList.size - 1) {
        if (currentIndex < surveyList.size ) {
            currentIndex++ // ✅ currentIndex 업데이트!
            updateSurvey(surveyList[currentIndex])
            // updateProgress()는 updateSurvey() 안에서 이미 호출되므로 제거
        } else {
            // 마지막 설문 완료
            // TODO: 결과 처리
        }
    }

    // 설문 항목 업데이트 함수
    private fun updateSurvey(surveyItem: Survey) {
        android.util.Log.d(
                "SurveyActivity",
                "updateSurvey 호출됨: currentIndex=$currentIndex, question=${surveyItem.question}, options=${surveyItem.options}"
        )

        tvQuestion.text = surveyItem.question

        if (surveyItem.options.size > 2) {
            // 마지막 설문 (여행 활동 선택) - RecyclerView 사용
            android.util.Log.d("SurveyActivity", "마지막 설문 (여행 활동 선택) - RecyclerView 표시")
            showRecyclerView(surveyItem.options)
            hideChoiceButtons()
        } else {
            // 일반 설문 - 기존 버튼 사용
            android.util.Log.d("SurveyActivity", "일반 설문 - 2개 선택지 버튼 표시")
            showChoiceButtons()
            hideRecyclerView()
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

            // SurveyOptionAdapter를 사용하여 RecyclerView 설정
            val adapter =
                    SurveyOptionAdapter(options) { option ->
                        // 옵션 선택 시 처리
                        android.util.Log.d("SurveyActivity", "선택된 옵션: $option")
                        // 0.5초 후에 다음 설문으로 이동 (색상 변경이 보이도록)
                        handler.postDelayed({ handleChoice(0) }, 500)
                    }

            recyclerView.adapter = adapter
            android.util.Log.d("SurveyActivity", "RecyclerView 어댑터 설정 완료")
        } catch (e: Exception) {
            android.util.Log.e("SurveyActivity", "showRecyclerView 오류: ${e.message}", e)
        }
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

    private fun updateProgress() {
        val surveyList = SurveyList().surveyList
        // 진행률 계산 수정: 0~100% 범위로 표시
        val progress = ((currentIndex ) * 100) / surveyList.size
        progressBar.progress = progress
        textViewDownload.text = "$progress%"

        // 디버깅: 진행률 계산 확인
        android.util.Log.d(
                "SurveyActivity",
                "진행률 업데이트: currentIndex=$currentIndex, progress=$progress%, total=${surveyList.size}"
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
}
