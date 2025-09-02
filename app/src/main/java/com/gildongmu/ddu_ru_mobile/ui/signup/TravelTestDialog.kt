package com.gildongmu.ddu_ru_mobile.ui.signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.gildongmu.ddu_ru_mobile.R
import com.gildongmu.ddu_ru_mobile.databinding.DialogTravelPreferenceTestStartBinding
import com.gildongmu.ddu_ru_mobile.model.signup.api.SurveyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TravelTestDialog
    : BottomSheetDialogFragment(R.layout.dialog_travel_preference_test_start) {

    private var _binding: DialogTravelPreferenceTestStartBinding? = null
    private val binding get() = _binding!!   // onViewCreated~onDestroyView 사이에서만 사용

    private val surveyViewModel: SurveyViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = DialogTravelPreferenceTestStartBinding.bind(view)

        binding.btnLater.setOnClickListener {
            surveyViewModel.resetSurveyKeepNickname()
            dismiss()
        }

        binding.btnStart.setOnClickListener {
            dismiss()
            startActivity(Intent(requireContext(), SurveyActivity::class.java))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
