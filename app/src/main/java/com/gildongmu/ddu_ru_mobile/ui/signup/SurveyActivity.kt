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
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gildongmu.ddu_ru_mobile.R
import com.gildongmu.ddu_ru_mobile.model.signup.api.SurveyViewModel
import com.gildongmu.ddu_ru_mobile.model.signup.survey.SurveyList

class SurveyActivity : AppCompatActivity() {
    private val vm: SurveyViewModel by viewModels()

    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnChoice1: Button
    private lateinit var btnChoice2: Button
    private lateinit var tvQuestion: TextView
    private lateinit var btnPrev: ImageView
    private lateinit var btnComplete: Button

    private val handler = Handler(Looper.getMainLooper())

    private val surveyList = SurveyList().surveyList
    private lateinit var nav: SurveyNavigator
    private lateinit var lastController: LastSurveyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)
        bindViews()

        nav = SurveyNavigator(surveyList)
        lastController = LastSurveyController(recyclerView, progressBar, btnComplete, nav, vm)

        progressBar.progressTintList =
            ColorStateList.valueOf(ContextCompat.getColor(this, R.color.mainColor))

        renderCurrent()
        observeVm()

        btnPrev.setOnClickListener {
            if (nav.prev()) renderCurrent()
        }

        // 선택 버튼 클릭 시 (왼쪽)
        btnChoice1.setOnClickListener {
            setSelectedStyle(btnChoice1, btnChoice2)
            handler.postDelayed({
                vm.setSingleOption(0, nav.current)
                if (nav.next()) renderCurrent()
            }, 500)
        }

        // 선택 버튼 클릭 시 (오른쪽)
        btnChoice2.setOnClickListener {
            setSelectedStyle(btnChoice2, btnChoice1)
            handler.postDelayed({
                vm.setSingleOption(1, nav.current)
                if (nav.next()) renderCurrent()
            }, 500)
        }

        btnComplete.setOnClickListener {
            lastController.submit()
            vm.submitSurveyToServer()
        }
    }

    private fun bindViews() {
        tvQuestion = findViewById(R.id.tvQuestion)
        btnChoice1 = findViewById(R.id.btnChoice1)
        btnChoice2 = findViewById(R.id.btnChoice2)
        btnPrev = findViewById(R.id.btnPrev)
        btnComplete = findViewById(R.id.btnComplete)
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBarMain)
    }

    private fun renderCurrent() {
        val item = nav.current
        tvQuestion.text = item.question
        progressBar.progress = nav.progressPercent()

        if (item.options.size > 2) {
            // 마지막 문항
            recyclerView.show()
            btnComplete.show()
            btnPrev.show()
            btnChoice1.hide()
            btnChoice2.hide()

            lastController.bind(item.options)
        } else {
            // 일반 2지선다
            recyclerView.hide()
            btnComplete.hide()
            btnPrev.show()
            btnChoice1.show()
            btnChoice2.show()

            btnChoice1.text = item.options[0]
            btnChoice2.text = item.options[1]
            resetChoiceStyles()
        }
    }

    private fun observeVm() {
        vm.isLoading.observe(this) { isLoading ->
            btnComplete.isEnabled = !isLoading
            btnComplete.text = if (isLoading) "전송 중..." else "완료"
        }
        vm.submitSuccess.observe(this) { success ->
            if (success) showToast("설문 제출이 완료되었습니다!")
        }
        vm.submitError.observe(this) { error ->
            error?.let { showToast("오류: $it") }
        }
    }

    private fun setSelectedStyle(selected: Button, other: Button) {
        selected.backgroundTintList = getColorStateList(R.color.mainColor)
        selected.setTextColor(getColor(android.R.color.white))
        other.backgroundTintList = getColorStateList(R.color.gray)
        other.setTextColor(getColor(android.R.color.black))
    }

    private fun resetChoiceStyles() {
        btnChoice1.backgroundTintList = getColorStateList(R.color.gray)
        btnChoice1.setTextColor(getColor(android.R.color.black))
        btnChoice2.backgroundTintList = getColorStateList(R.color.gray)
        btnChoice2.setTextColor(getColor(android.R.color.black))
    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
    fun View.show() { visibility = View.VISIBLE }
    fun View.hide() { visibility = View.GONE }

}
