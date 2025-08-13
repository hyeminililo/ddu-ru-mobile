package com.gildongmu.ddu_ru_mobile


import android.os.Bundle
import android.text.InputFilter
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.button.MaterialButton
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.content.Context
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val backText = findViewById<TextView>(R.id.tvBack)
        backText.setOnClickListener {
            finish()
        }


        val nicknameEdit = findViewById<EditText>(R.id.form_text_input)
        val btnDone = findViewById<MaterialButton>(R.id.btnDone)
        val subtitle = findViewById<TextView>(R.id.tvSubtitle)


        val maxLength = 12
        subtitle.text = getString(R.string.sub_hint_nickname, 0, maxLength)
        nicknameEdit.filters = arrayOf(InputFilter.LengthFilter(maxLength))

        nicknameEdit.addTextChangedListener { editable ->
            val text = editable?.toString()?.trim().orEmpty()
            val length = text.length

            subtitle.text = getString(R.string.sub_hint_nickname, length, maxLength)


            btnDone.isEnabled = text.isNotEmpty()
            btnDone.backgroundTintList = ContextCompat.getColorStateList(this,
                if (text.isNotEmpty()) R.color.mainColor else R.color.gray
            )
        }
        nicknameEdit.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(nicknameEdit.windowToken, 0)


                btnDone.performClick()
                true
            } else {
                false
            }
        }



        btnDone.setOnClickListener {
            val nickname = nicknameEdit.text?.toString()?.trim().orEmpty()

            if (nickname.isNotEmpty()) {
                // TODO: 원하는 동작 작성

            }
        }
    }
}
