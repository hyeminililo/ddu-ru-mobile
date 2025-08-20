package com.gildongmu.ddu_ru_mobile.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.gildongmu.ddu_ru_mobile.R

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TravelTestDialog : BottomSheetDialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_travel_preference_test_start, container, false
        )
        view.findViewById<Button>(R.id.btnLater).setOnClickListener {
            dismiss()
        }

        view.findViewById<Button>(R.id.btnStart).setOnClickListener {
            dismiss()
            startActivity(Intent(requireContext(), SurveyActivity::class.java))

        }
        return view
    }
}