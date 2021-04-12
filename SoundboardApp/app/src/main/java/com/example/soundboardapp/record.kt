package com.example.soundboardapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.nio.file.CopyOption
import java.nio.file.Files

class record : AppCompatActivity() {
    private var mediaRecorder: MediaRecorder?= null
    private var state: Boolean = false

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

        setContentView(R.layout.activity_record)

        val play = findViewById<Button>(R.id.play)
        val stop = findViewById<Button>(R.id.stop)
        val record = findViewById<Button>(R.id.record)
        val output = File(applicationContext.filesDir, "recording.mp3")
        mediaRecorder = MediaRecorder()

        //start recording on start press - everything else is media player initialization
        record.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                startRecording(output)
            }
        }
        stop.setOnClickListener {
            stopRecording()
        }
        play.setOnClickListener {
            playRecording(output)
        }
    }

    private fun startRecording(output: File){
        try{
            //media recorder set up & start
            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder?.setOutputFile(output.toString())

            mediaRecorder?.prepare()
            mediaRecorder?.start()
            state = true
            Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show()

        } catch (e: IllegalStateException){
            e.printStackTrace()
        } catch(e: IOException){
            e.printStackTrace()
        }
    }

    private fun stopRecording(){
        //media recorder stop and release
        if(state){
            mediaRecorder?.stop()
            mediaRecorder?.reset()
            mediaRecorder?.release()
            state = false
        } else{
            Toast.makeText(this, "Recording stopped", Toast.LENGTH_SHORT).show()
        }
    }

    private fun playRecording(output: File){
        val mediaPlayer = MediaPlayer()

        try{
            mediaPlayer.setDataSource(output.toString())
            mediaPlayer.prepare()
            mediaPlayer.start()
            Toast.makeText(this, "Play Back", Toast.LENGTH_SHORT).show()
        } catch(e: Exception){
            e.printStackTrace()
        }
    }

    fun selectSaveSlot(view: View) {
        //call select slot activity then saveSound
        val intent = Intent(applicationContext, selectSlot::class.java)
        intent.putExtra("retTo", "record")
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val slot = data?.getIntExtra("slot", 1)
                if (slot != null) {
                    saveSound(slot)
                }
            }
        }
    }

    private fun saveSound(slot: Int){
        val name = findViewById<EditText>(R.id.plain_text_input) //get name from UI

        //copy contents from temp output to savedName where name = recording<slot#>.mp3
        //overwrite file to record another sound for the slot
        val output = File(applicationContext.filesDir, "recording.mp3")
        val savedName = "recording".plus(slot.toString()).plus(".mp3")
        val savedFile = File(applicationContext.filesDir, savedName)
        output.copyTo(savedFile, overwrite = true)

        //save to database then start main activity
        //onCreate will update soundArray & UI
        val dbh = DBHandler(this, null, null, 1)
        dbh.addSound(slot, name.text.toString(), savedFile.toUri().toString())
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}