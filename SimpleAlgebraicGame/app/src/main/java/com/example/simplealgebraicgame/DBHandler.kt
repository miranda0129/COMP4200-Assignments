package com.example.simplealgebraicgame

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.content.ContentValues

class DBHandler(context: Context, name: String?,
                factory: SQLiteDatabase.CursorFactory?, version: Int) :
                    SQLiteOpenHelper(context, DATABASE_NAME,
                    factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_SCORE_TABLE = ("CREATE TABLE " +
                TABLE_SCORE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_USERNAME
                + " TEXT," + COLUMN_SCORE + " INTEGER" + ")")
        db.execSQL(CREATE_SCORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int,
                           newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE)
        onCreate(db)
    }

    fun addScore(score: Score) {

        val values = ContentValues()
        values.put(COLUMN_USERNAME, score.username)
        values.put(COLUMN_SCORE, score.score)

        val db = this.writableDatabase

        db.insert(TABLE_SCORE, null, values)
        db.close()
    }

    fun findUserScores(username: String): ArrayList<Score> {
        val query =
            "SELECT * FROM $TABLE_SCORE WHERE $COLUMN_USERNAME =  \"$username\""

        val db = this.writableDatabase

        val cursor = db.rawQuery(query, null)

        var scoreArray = ArrayList<Score>()

        while (cursor.moveToNext()) {

            val id = Integer.parseInt(cursor.getString(0))
            val username = cursor.getString(1)
            val score = Integer.parseInt(cursor.getString(2))
            scoreArray.add(Score(id, username, score))

        }
        cursor.close()
        db.close()
        return scoreArray
    }

    companion object {

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "scoreDB.db"
        val TABLE_SCORE = "scores"

        val COLUMN_ID = "_id"
        val COLUMN_USERNAME = "username"
        val COLUMN_SCORE = "score"
    }
}