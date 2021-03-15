package com.example.simplealgebraicgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn = findViewById<Button>(R.id.start)

        startBtn.setOnClickListener(){
            println("Clicked")

            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
    }
}