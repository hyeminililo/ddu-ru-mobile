package com.gildongmu.ddu_ru_mobile.ui.signup

import android.os.Bundle
import android.text.InputFilter
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.content.Context

import com.example.ddu_ru_mobile.ui.signup.TravelTestDialog
import com.gildongmu.ddu_ru_mobile.R

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_nickname)

        val backText = findViewById<TextView>(R.id.tvBack)
        backText.setOnClickListener { finish() }

        val nicknameEdit = findViewById<EditText>(R.id.form_text_input)
        val btnDone = findViewById<MaterialButton>(R.id.btnDone)
        val subtitle = findViewById<TextView>(R.id.tvSubtitle)
        val maxLength = 12

        subtitle.text = getString(R.string.sub_hint_nickname, 0, maxLength)
        nicknameEdit.filters = arrayOf(InputFilter.LengthFilter(maxLength))
        nicknameEdit.addTextChangedListener(
            NicknameWatcher(this, nicknameEdit, subtitle, btnDone, maxLength)
        )

        nicknameEdit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(nicknameEdit.windowToken, 0)
                btnDone.performClick()
                true
            } else false
        }

        btnDone.setOnClickListener {
            val nickname = nicknameEdit.text?.toString()?.replace("\\s".toRegex(), "") ?: ""
            // 니중에 토큰으로 대체
            val userId = ""
            if (nickname.isNotEmpty()) {

                // TODO: 원하는 동작 (서버로 전송 등)
                TravelTestDialog().show(supportFragmentManager, "TravelTestDialog")

            }
        }
    }
}
