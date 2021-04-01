package com.example.simplealgebraicgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Game : AppCompatActivity() {
    var startTime = 25000L
    var score = 0
    var attempted = 0
    var answer = ""
    var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        username = intent.getStringExtra("username").toString()

        findViewById<Button>(R.id.reset).setOnClickListener{ startGame() }
    }

    private fun startGame(){//called when activity begins and capable for reset
        score = 0
        attempted = 0


        findViewById<TextView>(R.id.score).text = score.toString()  //set score text to 0
        findViewById<TextView>(R.id.feedback).visibility = View.INVISIBLE
        findViewById<Button>(R.id.reset).visibility = View.INVISIBLE

        startTimer()
        displayNextQuestion()
    }

    private fun startTimer(){
        var time = startTime
        val timer = object : CountDownTimer(time, 1000)
        {
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                time = millisUntilFinished
                updateUI(time)
            }
        }
        timer.start()
    }

    fun updateUI(time: Long){ //for timer
        val timeText = findViewById<TextView>(R.id.time)
        timeText.text = (time/1000).toString()
    }

    private fun displayNextQuestion(){ //generate question and calculate correct answer
        var firstInt = Random.nextInt(0, 100)
        var secondInt = Random.nextInt(0, 100)
        answer = (firstInt + secondInt).toString()

        //display expression
        var expression = "".plus(firstInt).plus(" + ").plus(secondInt)
        findViewById<TextView>(R.id.expression).text = expression

        //generate and display answer options
        generateAnswers(answer)
    }

    private fun generateAnswers(answer: String){ //generate other 3 options
        val box1 = findViewById<Button>(R.id.ans1)
        val box2 = findViewById<Button>(R.id.ans2)
        val box3 = findViewById<Button>(R.id.ans3)
        val box4 = findViewById<Button>(R.id.ans4)

        val array = arrayOf(box1, box2, box3, box4)
        val correctAnswer = Random.nextInt(1, 4)

        array.forEachIndexed{index, button ->
            if(index == correctAnswer){ //add correct answer to randomly selected button
                button.text = answer
            }
            else{ //fill others with random numbers
                button.text = Random.nextInt(0, 100).toString()
                while(button.text == answer.toString()){ //ensure random num isn't the same as answer
                    button.text = Random.nextInt(0, 100).toString()
                }
            }
        }
    }

    fun checkAnswer(view: View) {
        with (view as Button){
            var i = "$text"
            if (i == answer) { //hard coded answer will be changed for A4
                score++
                showFeedback(true)
            }
            else { showFeedback(false) }
            attempted++
            updateScore()
            displayNextQuestion()
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

        writeScore(username, score)
    }

    fun changeTime(newTime: Long){
        startTime = newTime
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.time10 -> changeTime(10000L)
            R.id.time15 -> changeTime(15000L)
            R.id.time20 -> changeTime(20000L)
            R.id.Showscores -> { val intent = Intent(this, show_scores::class.java)
                intent.putExtra("username", username)
                startActivity(intent)}
        }
        return(super.onOptionsItemSelected(item))
    }

    fun writeScore(username: String, score: Int){
        val dbh = DBHandler(this, null, null, 1)

        val entry = Score(username, score)
        dbh.addScore(entry)
    }

    fun changeUsername(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
