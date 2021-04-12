package com.example.soundboardapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private var mMediaPlayer: MediaPlayer = MediaPlayer()
    private var soundArray: ArrayList<Sound>?= null
    private val buttonArray = ArrayList<Button>()

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

        setContentView(R.layout.activity_main)


        //initialize button array
        buttonArray.add( findViewById(R.id.one))
        buttonArray.add( findViewById(R.id.two))
        buttonArray.add( findViewById(R.id.three))
        buttonArray.add( findViewById(R.id.four))
        buttonArray.add( findViewById(R.id.five))
        buttonArray.add( findViewById(R.id.six))
        buttonArray.add( findViewById(R.id.seven))
        buttonArray.add( findViewById(R.id.eight))
        buttonArray.add( findViewById(R.id.nine))


        //initialize on click listeners to play sounds
        buttonArray[0].setOnClickListener { playSound(0) }
        buttonArray[1].setOnClickListener { playSound(1) }
        buttonArray[2].setOnClickListener { playSound(2) }
        buttonArray[3].setOnClickListener { playSound(3) }
        buttonArray[4].setOnClickListener { playSound(4) }
        buttonArray[5].setOnClickListener { playSound(5) }
        buttonArray[6].setOnClickListener { playSound(6) }
        buttonArray[7].setOnClickListener { playSound(7) }
        buttonArray[8].setOnClickListener { playSound(8) }

        getSounds() //get sounds from database and populate soundArray
        displayNames(buttonArray)
    }

    private fun playSound(index: Int) {
            val path = soundArray?.get(index)?.sound
            println(index)

            //use media player to play sound from path
            mMediaPlayer = path?.let { MediaPlayer.create(this, it) }!!
            mMediaPlayer.isLooping = false
            mMediaPlayer.start()
    }

    private fun getSounds(){
        //populate sound array from database at start up
        val dbh = DBHandler(this, null, null, 1)
        soundArray = dbh.fillSoundArray()
    }

    @Override
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    @Override
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.record -> {
                val intent = Intent(applicationContext, record::class.java)
                startActivity(intent)
            }
            R.id.resetBoard -> {
                populateDefaultSounds()
            }
            R.id.settings -> {
                val intent = Intent(applicationContext, settings::class.java)
                startActivity(intent)
            }
        }
        return(super.onOptionsItemSelected(item))
    }

    //starts timer activity
    fun timeIntent(view: View) {
        val intent = Intent(applicationContext, setTime::class.java)
        startActivityForResult(intent, 1)
    }

    //activity result from timer - play designated sound - timer is finished
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //get slot number to play
        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val slot = data?.getIntExtra("slot", 1)
                if (slot != null) {
                    playSound(slot-1)
                }
            }
        }
    }

    //updates database to initial sounds from res/raw, update sound array & UI
    private fun populateDefaultSounds(){
        val dbh = DBHandler(this, null, null, 1)

        dbh.addSound(1, "Firecrack", Uri.parse("android.resource://$packageName/raw/firecrack").toString())
        dbh.addSound(2, "Fart", Uri.parse("android.resource://$packageName/raw/fart").toString())
        dbh.addSound(3, "Pop", Uri.parse("android.resource://$packageName/raw/pop").toString())
        dbh.addSound(4, "Drums", Uri.parse("android.resource://$packageName/raw/drums").toString())
        dbh.addSound(5, "Pew", Uri.parse("android.resource://$packageName/raw/pew").toString())
        dbh.addSound(6, "Crickets", Uri.parse("android.resource://$packageName/raw/crickets").toString())
        dbh.addSound(7, "Dun", Uri.parse("android.resource://$packageName/raw/dun").toString())
        dbh.addSound(8, "knock", Uri.parse("android.resource://$packageName/raw/knock").toString())
        dbh.addSound(9, "Radar", Uri.parse("android.resource://$packageName/raw/radar").toString())

        getSounds() //get sounds from database and populate soundArray
        displayNames(buttonArray)
    }

    //get names from sound array and display text on buttons
    private fun displayNames(ButtonArray: ArrayList<Button>){
        ButtonArray[0].text = soundArray?.get(0)?.name ?: "One"
        ButtonArray[1].text = soundArray?.get(1)?.name ?: "Two"
        ButtonArray[2].text = soundArray?.get(2)?.name ?: "Three"
        ButtonArray[3].text = soundArray?.get(3)?.name ?: "Four"
        ButtonArray[4].text = soundArray?.get(4)?.name ?: "Five"
        ButtonArray[5].text = soundArray?.get(5)?.name ?: "Six"
        ButtonArray[6].text = soundArray?.get(6)?.name ?: "Seven"
        ButtonArray[7].text = soundArray?.get(7)?.name ?: "Eight"
        ButtonArray[8].text = soundArray?.get(8)?.name ?: "Nine"
    }

}
