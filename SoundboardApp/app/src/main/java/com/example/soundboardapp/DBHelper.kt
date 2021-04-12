package com.example.soundboardapp
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class DBHandler(context: Context, name: String?,
                factory: SQLiteDatabase.CursorFactory?, version: Int) :
        SQLiteOpenHelper(context, DATABASE_NAME,
                factory, DATABASE_VERSION) {

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "soundDB.db"
        val TABLE_SOUNDS = "sounds"

        val COLUMN_INDEX = "_index"
        val COLUMN_NAME = "name"
        val COLUMN_SOUND = "sound "
    }


    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SCORE_TABLE = ("CREATE TABLE " +
                TABLE_SOUNDS + "("
                + COLUMN_INDEX + " INTEGER PRIMARY KEY," +
                COLUMN_NAME
                + " TEXT," + COLUMN_SOUND + " BLOB" + ")")
        db.execSQL(CREATE_SCORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOUNDS)
        onCreate(db)
    }

    //uses SQL UPDATE as indexes are static
    fun addSound(slot: Int, name: String, sound: String) {
      val UPDATE_SOUND = (
               "UPDATE " + TABLE_SOUNDS + " SET " + COLUMN_SOUND + " = \'" + sound +
                       "\' WHERE " + slot + " = " + COLUMN_INDEX
               )
        val UPDATE_NAME = (
                "UPDATE " + TABLE_SOUNDS + " SET " + COLUMN_NAME + " = \'" + name +
                        "\' WHERE " + slot + " = " + COLUMN_INDEX
                )

        val db = this.writableDatabase
        db.execSQL(UPDATE_SOUND)
        db.execSQL(UPDATE_NAME)
    }

    //get all database entries and fill sound array for access
    fun fillSoundArray(): ArrayList<Sound> {
        val query =
                "SELECT * FROM $TABLE_SOUNDS"

        val db = this.writableDatabase
        val cursor = db.rawQuery(query, null)

        var soundArray = ArrayList<Sound>()

        while (cursor.moveToNext()) {
            val index = Integer.parseInt(cursor.getString(0))
            val name = cursor.getString(1)
            val sound = cursor.getString(2).toUri()
            soundArray.add(Sound(index, name, sound))
        }
        cursor.close()
        db.close()
        return soundArray
    }
}