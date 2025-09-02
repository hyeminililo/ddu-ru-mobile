package com.gildongmu.ddu_ru_mobile.ui.signup

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import androidx.core.content.ContextCompat
import com.gildongmu.ddu_ru_mobile.R

class NicknameWatcher(
    private val context: Context,
    private val editText: EditText,
    private val subtitle: TextView,
    private val btnDone: MaterialButton,
    private val maxLength: Int = 12
) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        val raw = s?.toString() ?: ""
        val onlyText = raw.replace("\\s".toRegex(), "")
        val length = onlyText.length
        subtitle.text = context.getString(R.string.sub_hint_nickname, length, maxLength)
        btnDone.isEnabled = onlyText.isNotEmpty()
        btnDone.backgroundTintList = ContextCompat.getColorStateList(
            context, if (onlyText.isNotEmpty()) R.color.mainColor else R.color.gray
        )
    }
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}

