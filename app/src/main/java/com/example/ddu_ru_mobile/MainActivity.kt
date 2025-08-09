package com.gildongmu.ddu_ru_mobile

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nicknameEdit = findViewById<EditText>(R.id.form_text_input)
        val btnDone = findViewById<Button>(R.id.btnDone)
        val mainColor = ContextCompat.getColor(this, R.color.mainColor)
        val grayColor = ContextCompat.getColor(this, R.color.gray)

        nicknameEdit.addTextChangedListener {
            val text = it.toString().trim()


            if (text.isNotEmpty()) {

                btnDone.setBackgroundColor(mainColor)
            } else {

                btnDone.setBackgroundColor(grayColor)
            }
        }
    }
}
