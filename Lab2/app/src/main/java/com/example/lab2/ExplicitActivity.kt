package com.example.lab2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ExplicitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explicit)

        val text = findViewById<EditText>(R.id.plain_text_input)
        val ret = findViewById<Button>(R.id.Enter) as Button

        ret.setOnClickListener(){
            val returnIntent =  Intent(this, MainActivity::class.java)
            returnIntent.putExtra("UserInput", text.text.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}