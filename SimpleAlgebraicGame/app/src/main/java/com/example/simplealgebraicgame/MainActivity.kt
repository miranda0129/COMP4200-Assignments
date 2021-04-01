package com.example.simplealgebraicgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startBtn = findViewById<Button>(R.id.start)
        val textIn = findViewById<EditText>(R.id.textIn)

        startBtn.setOnClickListener(){
            if(textIn.text.toString() == ""){
                println("null detected")
                Toast.makeText(applicationContext, "correct", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, Game::class.java)
                intent.putExtra("username", textIn.text.toString())
                startActivity(intent)
            }
        }
    }
}

