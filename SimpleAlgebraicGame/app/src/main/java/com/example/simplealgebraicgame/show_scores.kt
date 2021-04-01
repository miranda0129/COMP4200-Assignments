package com.example.simplealgebraicgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class show_scores : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_scores)

        val scoreText = findViewById<TextView>(R.id.scores)
        val title = findViewById<TextView>(R.id.username)
        val username = intent.getStringExtra("username")

        val backBtn = findViewById<Button>(R.id.back)
        backBtn.setOnClickListener(){
            val intent = Intent(this, Game::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

        title.text = "Scores for ".plus(username)

        if(username != null){
            val scoreList = readScores(username)
            var displayStr = ""

            for (item in scoreList){
                displayStr = displayStr.plus(item.score.toString()).plus("\n")
            }
            scoreText.text = displayStr
            println(displayStr)
        }
       else { scoreText.text = "No scores for the given username"}

    }

    fun readScores(username: String): ArrayList<Score> {
        val dbh = DBHandler(this, null, null, 1)
        return dbh.findUserScores(username)
    }
}