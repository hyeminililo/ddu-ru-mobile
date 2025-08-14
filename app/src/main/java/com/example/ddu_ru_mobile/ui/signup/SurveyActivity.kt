package com.example.ddu_ru_mobile.ui.signup

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ddu_ru_mobile.model.signup.survey.Survey
import com.example.ddu_ru_mobile.model.signup.survey.SurveyList
import com.gildongmu.ddu_ru_mobile.R

class SurveyActivity : AppCompatActivity() {

    private var currentIndex = 0 // 현재 설문 항목의 인덱스

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val btnChoice1 = findViewById<Button>(R.id.btnChoice1)
        val btnChoice2 = findViewById<Button>(R.id.btnChoice2)
        val btnPrev = findViewById<Button>(R.id.btnPrev)
        val tvSurveyTitle = findViewById<TextView>(R.id.tvSurveyTitle)

        val surveyList = SurveyList().surveyList

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
            // 사용자가 첫 번째 옵션을 선택한 경우에 대한 처리
        }

        btnChoice2.setOnClickListener {
            // 사용자가 두 번째 옵션을 선택한 경우에 대한 처리
        }
    }

    // 설문 항목 업데이트 함수
    private fun updateSurvey(surveyItem: Survey) {
        val tvQuestion = findViewById<TextView>(R.id.tvQuestion)
        val btnChoice1 = findViewById<Button>(R.id.btnChoice1)
        val btnChoice2 = findViewById<Button>(R.id.btnChoice2)

        tvQuestion.text = surveyItem.question
        btnChoice1.text = surveyItem.options[0]
        btnChoice2.text = surveyItem.options[1]
    }
}
