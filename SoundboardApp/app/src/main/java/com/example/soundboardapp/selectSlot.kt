package com.example.soundboardapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class selectSlot : AppCompatActivity() {
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

        setContentView(R.layout.activity_select_slot)

        //obtain slot to swap and call retFun
        findViewById<Button>(R.id.one).setOnClickListener   { retFun(1) }
        findViewById<Button>(R.id.two).setOnClickListener   { retFun(2) }
        findViewById<Button>(R.id.three).setOnClickListener { retFun(3) }
        findViewById<Button>(R.id.four).setOnClickListener  { retFun(4) }
        findViewById<Button>(R.id.five).setOnClickListener  { retFun(5) }
        findViewById<Button>(R.id.six).setOnClickListener   { retFun(6) }
        findViewById<Button>(R.id.seven).setOnClickListener { retFun(7) }
        findViewById<Button>(R.id.eight).setOnClickListener { retFun(8) }
        findViewById<Button>(R.id.nine).setOnClickListener  { retFun(9) }
    }

    //return to calling activity
    private fun retFun(slot: Int){
        val retTo = intent.getStringExtra("retTo")

        if(retTo == "newSound"){
            val returnIntent = Intent(applicationContext, newSound::class.java)
            returnIntent.putExtra("slot", slot)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        if(retTo == "setTime"){
            val returnIntent = Intent(applicationContext, setTime::class.java)
            returnIntent.putExtra("slot", slot)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        if(retTo == "record"){
            val returnIntent = Intent(applicationContext, setTime::class.java)
            returnIntent.putExtra("slot", slot)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}