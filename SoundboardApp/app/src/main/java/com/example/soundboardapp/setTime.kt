package com.example.soundboardapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView

class setTime : AppCompatActivity() {
    var time = 10000L //in milliseconds
    private lateinit var timer: CountDownTimer

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

        setContentView(R.layout.activity_set_time)

        displayTime()

        findViewById<Button>(R.id.startButton).setOnClickListener(){
            val intent = Intent(applicationContext, selectSlot::class.java)
            intent.putExtra("retTo", "setTime")
            startActivityForResult(intent, 1)
        }
    }

    fun increaseTime(view: View) {
        if(time<99000) {
            time += 1000
            displayTime()

        }
    }

    fun decreaseTime(view: View) {
        if(time > 1000)  time -= 1000
        displayTime()
    }

    @SuppressLint("SetTextI18n")
    private fun displayTime(){
        val timeView = findViewById<TextView>(R.id.timeView)
        timeView.text = (time / 1000).toString()

        if(time >= 60000){
            val totalSec = time / 1000 //get mins and secs
            val min = totalSec / 60
            val sec = totalSec % 60

            //add preceding 0 if single digit
            var secFormat = sec.toString()
            if(sec < 10) {
                secFormat = "0".plus(sec.toString())
            }

            //print to ui
            timeView.text = min.toString() + ":" + secFormat
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //get slot
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val slot = data?.getIntExtra("slot", 1)
                if (slot != null) {
                    start(slot)
                }
            }
        }
    }

    private fun start(slot: Int) {
        //create and start timer
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished
                displayTime()
            }

            //play sound when timer is done
            override fun onFinish() {
                //intent back to main activity with slot # to play
                val returnIntent = Intent(applicationContext, MainActivity::class.java)
                returnIntent.putExtra("slot", slot)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }.start()
    }
}

