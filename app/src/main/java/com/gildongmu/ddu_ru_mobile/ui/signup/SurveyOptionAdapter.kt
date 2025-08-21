package com.gildongmu.ddu_ru_mobile.ui.signup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.gildongmu.ddu_ru_mobile.R

/**
 * 여행 활동 선택을 위한 RecyclerView 어댑터
 *
 * 기능:
 * - 9개의 여행 활동 옵션을 리스트로 표시
 * - 최대 3개까지 선택 가능
 * - 선택 상태에 따른 버튼 스타일 변경
 * - 선택된 옵션들을 콜백으로 전달
 */
class SurveyOptionAdapter(
        private val options: List<String>, // 9개의 여행 활동 옵션 리스트
        private val onOptionSelected: (String) -> Unit // 옵션 선택 시 호출되는 콜백
) : RecyclerView.Adapter<SurveyOptionAdapter.OptionViewHolder>() {

    // 선택된 옵션들을 저장하는 Set (중복 방지)
    private val selectedOptions = mutableSetOf<String>()

    /** 각 옵션 버튼을 담는 ViewHolder */
    inner class OptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: Button = itemView.findViewById(R.id.btnOption)

        /**
         * 옵션 데이터를 버튼에 바인딩
         * @param option 현재 표시할 옵션 텍스트
         */
        fun bind(option: String) {
            // 1. 버튼 텍스트/접근성 텍스트 설정
            button.text = option
            button.contentDescription = option

            // 2. 현재 선택 상태 설정
            button.isSelected = selectedOptions.contains(option)

            // 3. 선택 상태에 따른 버튼 스타일 변경
            updateButtonStyle(button, option)

            // 4. 클릭 리스너  설정
            button.setOnClickListener {
                toggleSelection(option) // 선택 상태 토글
                updateButtonStyle(button, option) // 스타일 업데이트
                onOptionSelected(option) // 콜백 호출
            }
        }

        /**
         * 선택 상태에 따라 버튼 스타일 변경
         * @param button 스타일을 변경할 버튼
         * @param option 해당 버튼의 옵션
         */
        private fun updateButtonStyle(button: Button, option: String) {
            if (selectedOptions.contains(option)) {
                // 선택된 상태: 메인 컬러 배경 + 흰색 텍스트
                button.backgroundTintList = itemView.context.getColorStateList(R.color.mainColor)
                button.setTextColor(itemView.context.getColor(android.R.color.white))
            } else {
                // 선택되지 않은 상태: 회색 배경 + 검은색 텍스트
                button.backgroundTintList =
                        itemView.context.getColorStateList(android.R.color.darker_gray)
                button.setTextColor(itemView.context.getColor(android.R.color.black))
            }
        }
    }

    /**
     * 옵션 선택 상태를 토글 (선택 ↔ 해제)
     * @param option 토글할 옵션
     */
    private fun toggleSelection(option: String) {
        if (selectedOptions.contains(option)) {
            // 이미 선택된 경우: 선택 해제
            selectedOptions.remove(option)
        } else {
            // 선택되지 않은 경우: 최대 3개까지만 선택 가능
            if (selectedOptions.size < 3) {
                selectedOptions.add(option)
            }
            // 3개 초과 선택 시도 시 무시 (사용자에게 알림 필요 시 추가)
        }
    }

    /** ViewHolder 생성 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        android.util.Log.d("SurveyOptionAdapter", "onCreateViewHolder 호출됨")
        val view =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_survey_option, parent, false)
        android.util.Log.d("SurveyOptionAdapter", "뷰 생성 완료: ${view.id}")
        return OptionViewHolder(view)
    }

    /** ViewHolder에 데이터 바인딩 */
    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        android.util.Log.d(
                "SurveyOptionAdapter",
                "onBindViewHolder 호출됨: position=$position, option=${options[position]}"
        )
        holder.bind(options[position])
    }

    /** 전체 아이템 개수 반환 */
    override fun getItemCount(): Int {
        android.util.Log.d("SurveyOptionAdapter", "getItemCount 호출됨: ${options.size}")
        return options.size
    }

    /** 현재 선택된 옵션들의 리스트 반환 */
    fun getSelectedOptions(): List<String> = selectedOptions.toList()
}
