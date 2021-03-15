package com.example.simplealgebraicgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView

class Game : AppCompatActivity() {
    var time = 25000L
    var score = 0
    var attempted = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        findViewById<Button>(R.id.reset).setOnClickListener{ startGame() }
        startGame()
    }

    private fun startGame(){//called when activity begins and capable for reset
        time = 25000L
        score = 0
        attempted = 0


        findViewById<TextView>(R.id.score).text = score.toString()  //set score text to 0
        findViewById<TextView>(R.id.feedback).visibility = View.INVISIBLE
        findViewById<Button>(R.id.reset).visibility = View.INVISIBLE

        startTimer()
        displayNextQuestion()
    }

    private fun startTimer(){
        val timer = object : CountDownTimer(time, 1000)
        {
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished
                updateUI()
            }
        }
        timer.start()
    }

    fun updateUI(){ //for timer
        val timeText = findViewById<TextView>(R.id.time)
        timeText.text = (time/1000).toString()
    }

    private fun displayNextQuestion(){ //generate question and calculate correct answer
        var firstInt = 45
        var secondInt = 5  //hardcoded values will be rand gen
        var answer = 50   // & calc answer by end of A4

        //display expression
        var expression = "".plus(firstInt).plus(" + ").plus(secondInt)
        findViewById<TextView>(R.id.expression).text = expression

        //generate and display answer options
        generateAnswers(50)
    }

    private fun generateAnswers(answer: Int){ //generate other 3 options
        var wrong1 = 60
        var wrong2 = 100
        var wrong3 = 3

        val box1 = findViewById<Button>(R.id.ans1)
        val box2 = findViewById<Button>(R.id.ans2)
        val box3 = findViewById<Button>(R.id.ans3)  //hardcoded values will be replaced with
        val box4 = findViewById<Button>(R.id.ans4)  //rand values in A4

        box1.text = answer.toString()
        box2.text = wrong1.toString()  //hardcoded placement will be
        box3.text = wrong2.toString()  //dynamic end of A4
        box4.text = wrong3.toString()
    }

    fun checkAnswer(view: View) {
        with (view as Button){
            var i = "$text"
            if (i == "50") { //hard coded answer will be changed for A4
                score++
                showFeedback(true)
            }
            else { showFeedback(false) }
            attempted++
            updateScore()
        }
    }

    private fun showFeedback(isCorrect: Boolean){ //show correct/incorrect - feedback view not available in checkAnswer due to view parameter
        val feedback = findViewById<TextView>(R.id.feedback)
        feedback.visibility = View.VISIBLE

        if (isCorrect) { feedback.text = "Correct!" }
        else { feedback.text = "Incorrect" }
    }

    private fun updateScore(){//on UI
        val scoreDisplay = findViewById<TextView>(R.id.score)
        scoreDisplay.text = score.toString().plus(" / ").plus(attempted.toString())
    }

    fun endGame(){//display final score and reset button
        val endText = findViewById<TextView>(R.id.feedback)
        endText.text = "Your score is: ".plus(score.toString()).plus(" / ").plus(attempted.toString())

        findViewById<TextView>(R.id.feedback).visibility = View.VISIBLE
        findViewById<Button>(R.id.reset).visibility = View.VISIBLE
    }

}