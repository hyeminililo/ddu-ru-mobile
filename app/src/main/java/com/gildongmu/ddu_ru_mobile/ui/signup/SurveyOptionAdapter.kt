package com.gildongmu.ddu_ru_mobile.ui.signup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.gildongmu.ddu_ru_mobile.R
import retrofit2.http.Body

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
    private val isSelected: (String) -> Boolean, // 옵션 선택 시 호출되는 콜백
    private val onToogle: (String) -> Unit
) : RecyclerView.Adapter<SurveyOptionAdapter.OptionViewHolder>() {

    // 선택된 옵션들을 저장하는 Set (중복 방지)
//    private val selectedOptions = mutableSetOf<String>()

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

            applyStyle(option)

            // 4. 클릭 리스너  설정
            button.setOnClickListener {
                onToogle(option) // 선택 상태 토글
                applyStyle(option)
            }
        }

        private fun applyStyle(option: String) {
            val ctx = itemView.context
            if (isSelected(option)) {
                button.backgroundTintList = ctx.getColorStateList(R.color.mainColor)
                button.setTextColor(ctx.getColor(android.R.color.white))
            } else {
                button.backgroundTintList = ctx.getColorStateList(R.color.gray)
                button.setTextColor(ctx.getColor(android.R.color.black))
            }
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

}
