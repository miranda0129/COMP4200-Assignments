package com.example.lab2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val eButton = findViewById<Button>(R.id.Explicit)
        val iButton = findViewById<Button>(R.id.Implicit)
        val textBox = findViewById<TextView>(R.id.TextBox)

        eButton.setOnClickListener(){
            val intent =  Intent(this, ExplicitActivity::class.java)
            startActivityForResult(intent, 1)
        }

        iButton.setOnClickListener(){
            val sendIntent: Intent = Intent().apply{
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textBox.text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                println("it worked")
                val textBox = findViewById<TextView>(R.id.TextBox)
                val reply = data?.getStringExtra("UserInput")
                textBox.text = reply
            }
        }
    }

}