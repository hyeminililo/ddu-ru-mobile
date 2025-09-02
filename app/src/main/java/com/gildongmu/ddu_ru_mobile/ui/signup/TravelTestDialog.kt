package com.gildongmu.ddu_ru_mobile.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gildongmu.ddu_ru_mobile.R
import com.gildongmu.ddu_ru_mobile.model.signup.api.SurveyViewModel
import com.gildongmu.ddu_ru_mobile.model.signup.survey.Servey
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TravelTestDialog : BottomSheetDialogFragment() {
    private val signupViewModel = SurveyViewModel()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.dialog_travel_preference_test_start, container, false)

        view.findViewById<Button>(R.id.btnLater).setOnClickListener {
            // surveyResult 초기화
            signupViewModel.surveyResult.value = Servey()

            Log.d("survey ===========", "${signupViewModel.nickName.value}")
            Log.d("survey ===========", "${signupViewModel.surveyResult.value}")
            dismiss()
        }

        view.findViewById<Button>(R.id.btnStart).setOnClickListener {
            dismiss()
            startActivity(Intent(requireContext(), SurveyActivity::class.java))
        }

        return view
    }
}
