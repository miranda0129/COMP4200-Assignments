package com.example.soundboardapp

import android.net.Uri
import java.io.File
import java.nio.channels.FileLockInterruptionException

class Sound {
    var index: Int = 0
    var name: String?=null
    var sound: Uri?= null

    constructor(index: Int, name: String, sound: Uri){
        this.index = index
        this.name = name
        this.sound = sound
    }
}