package com.example.timer

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var time = 30000L //in milliseconds
    private val default = 30000L
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        time = default
        displayTime()
    }

    //when start is clicked
    fun start(view: View) {

        //create and start timer
        timer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
               time = millisUntilFinished
                displayTime()
            }

            //notify when timer is done
            override fun onFinish() {
                timeView.text = "Done"
            }
        }.start()

        startButton.isEnabled = false
        resetButton.isEnabled = false
        incButton.isEnabled = false
        decButton.isEnabled = false
    }

    //increase time by 1 second
    fun increaseTime(view: View) {
        if(time<99000) {
            time += 1000
            displayTime()
        }
    }

    //decrease time by 1 second
    fun decreaseTime(view: View) {
        if(time > 1000)  time -= 1000
        displayTime()
    }

    //reset time to default
    fun reset(view: View) {
        time = default
        displayTime()
    }

    //stop timer
    fun stop(view: View) {
        timer.cancel()

        startButton.isEnabled = true
        resetButton.isEnabled = true
        incButton.isEnabled = true
        decButton.isEnabled = true
    }

    private fun displayTime(){
        timeView.text = (time / 1000).toString()

        if(time >= 60000){
            var totalSec = time / 1000 //get mins and secs
            var min = totalSec / 60
            var sec = totalSec % 60

            //add preceding 0 if single digit
            var secFormat = sec.toString()
            if(sec < 10) {
                secFormat = "0".plus(sec.toString())
            }

            //print to ui
            timeView.text = min.toString() + ":" + secFormat
        }
    }
}