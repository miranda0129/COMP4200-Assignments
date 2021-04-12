package com.example.soundboardapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //check sharedPref for theme
        val sharedPref = getSharedPreferences("com.example.soundboardapp.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE)
        val defaultValue = "purple"
        val theme = sharedPref.getString("theme", defaultValue)

        if(theme == "blue"){
            setTheme(R.style.altTheme)
        }
        if(theme == "purple"){
            setTheme((R.style.defaultTheme))
        }

        setContentView(R.layout.activity_settings)


        val purpleBtn = findViewById<Button>(R.id.themePurple)
        val blueBtn = findViewById<Button>(R.id.themeBlue)

        purpleBtn.setOnClickListener {
            with( sharedPref.edit() ){
                putString("theme", "purple")
                apply()

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

        blueBtn.setOnClickListener{
            with( sharedPref.edit() ){
                putString("theme", "blue")
                apply()

                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}