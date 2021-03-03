package com.example.tf_quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var questionNum = 0
    private var score = 0
    private val totalq = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val trueButton = findViewById<Button>(R.id.trueBtn)
        val falseButton = findViewById<Button>(R.id.falseBtn)
        val nextButton = findViewById<Button>(R.id.nextBtn)


        displayNextQuestion() //display first question
        nextButton.isEnabled = false

        trueButton.setOnClickListener(){
            nextButton.isEnabled = true

            showToastAnswer(UserA = 1)
        }

        falseButton.setOnClickListener(){
            nextButton.isEnabled = true

            showToastAnswer(UserA = 0)
        }

        nextButton.setOnClickListener(){ //display next question or reset game
            if(questionNum > totalq){
                resetGame()
            }
            else {
                nextButton.isEnabled = false
                displayNextQuestion()
            }
        }
    }

    private fun showToastAnswer(UserA: Int){
        val answers = arrayOf(1, 1, 0, 1, 1, 1) //array of answers

        if(UserA == answers[questionNum-1]){ //correct answer
            Toast.makeText(applicationContext, "correct", Toast.LENGTH_SHORT).show()
            score++
        }
        else{ //incorrect answer
            Toast.makeText(applicationContext, "incorrect", Toast.LENGTH_SHORT).show()
        }
    }


    private fun displayNextQuestion(){
        val questionString = findViewById<TextView>(R.id.question)

        //array of all questions for easy index access
        val questions = arrayOf(getString(R.string.question1), getString(R.string.question2), getString(R.string.question3), getString(R.string.question4),
        getString(R.string.question5), getString(R.string.question6))

        //display next question and increment counter
        if (questionNum++ < totalq){
            questionString.text = questions[questionNum-1]
        }
        //all questions are completed call showScore
        if(questionNum > totalq) {
            println(questionNum)
           endGame()
        }
    }

    private fun resetGame(){ //reset score, counter and buttons
        score = 0
        questionNum = 0

        findViewById<Button>(R.id.trueBtn).isEnabled = true
        findViewById<Button>(R.id.falseBtn).isEnabled = true
        findViewById<Button>(R.id.nextBtn).isEnabled = false

        displayNextQuestion()
    }

    private fun endGame(){ //show score & disable all buttons except renamed next (Reset)
        var nextButton = findViewById<Button>(R.id.nextBtn)
        var msg = "Your score is: ".plus(score).plus("/").plus(totalq)

        Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()

        findViewById<Button>(R.id.trueBtn).isEnabled = false
        findViewById<Button>(R.id.falseBtn).isEnabled = false
        nextButton.isEnabled = true
        nextButton.text = "Reset"
    }
}