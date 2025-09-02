package com.gildongmu.ddu_ru_mobile.ui.signup

import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gildongmu.ddu_ru_mobile.R
import com.gildongmu.ddu_ru_mobile.model.signup.api.SurveyViewModel

const val MAX_LAST_SELECTION = 3

class LastSurveyController(private val recyclerView: RecyclerView,
                           private val progressBar: ProgressBar,
                           private val btnComplete: Button,
                           private val navigator: SurveyNavigator,
                           private val viewModel: SurveyViewModel) {

    private val selected = linkedSetOf<String>()

    fun bind(options: List<String>){
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 3)
        recyclerView.adapter = SurveyOptionAdapter(options = options,
            isSelected = {opt -> selected.contains(opt)},
            onToogle = {opt -> toggle(opt)})
        updateProgressForLast()
        updateCompleteEnabled()

    }
    private fun toggle(option: String) {
        if (selected.contains(option)) {
            selected.remove(option)
        } else if (selected.size < MAX_LAST_SELECTION) {
            selected.add(option)
        } else {
            Log.d("LastSurveyController", "최대 ${MAX_LAST_SELECTION}개 선택 가능")
        }

        if (selected.size == MAX_LAST_SELECTION) {
            progressBar.progress = 100
        } else {
            updateProgressForLast()
        }
        updateCompleteEnabled()
    }
    fun submit() {
        if (selected.size < MAX_LAST_SELECTION) return
        selected.forEach { viewModel.toggleActivity(it) }
    }

    private fun updateProgressForLast() {
        val base = navigator.progressPercent()
        val step = (selected.size * 100) / MAX_LAST_SELECTION // 0, 33, 66
        progressBar.progress = base + step
    }

    private fun updateCompleteEnabled() {
        val can = selected.size >= MAX_LAST_SELECTION
        btnComplete.isEnabled = can
        if (can) {
            btnComplete.backgroundTintList =
                btnComplete.context.getColorStateList(R.color.mainColor)
            btnComplete.setTextColor(btnComplete.context.getColor(android.R.color.white))
        } else {
            btnComplete.background =
                btnComplete.context.getDrawable(R.drawable.button_complete_disabled)
            btnComplete.setTextColor(btnComplete.context.getColor(R.color.gray))
        }
    }
}
