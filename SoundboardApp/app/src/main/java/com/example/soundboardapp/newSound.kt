package com.example.soundboardapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.uiThread
import java.net.URL

class newSound : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_sound)

        doAsync {
            val result = URL("api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=553448cf11b9a2d60a8a75aacee2010f").readText()

            uiThread {
                println(result)
            }
        }
    }

    fun search(view: View) {
        val intent = Intent(applicationContext, selectSlot::class.java)
        intent.putExtra("retTo", "newSound")
        startActivityForResult(intent, 1)
    }

    //write new sound to database
    fun writeSound(slot: Int){
        //val dbh = DBHandler(this, null, null, 1)
        //dbh.addScore()

        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //get slot to swap and call writeSound
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val slot = data?.getIntExtra("slot", 1)
                if (slot != null) {
                    writeSound(slot)
                }
            }
        }
    }


}