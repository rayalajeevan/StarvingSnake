package com.example.starvingsnake

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseAdapter(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase) {

        val highscore_query = ("CREATE TABLE " + USER_TABLE_NAME + " ("
                + HIGHSCORE_USERID + " INTEGER PRIMARY KEY, " +
                HIGHSCORE_USER_NAME + " TEXT," +
                HIGHSCORE + " INTEGER," +
                HIGHSCORE_LEVEL+" TEXT "+
                ")")
        db.execSQL(highscore_query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME)
        onCreate(db)
    }


    fun addScore(username : String,score:Int,level:String){

        val values = ContentValues()
        values.put(HIGHSCORE_USER_NAME, username)
        values.put(HIGHSCORE, score)
        values.put(HIGHSCORE_LEVEL,level)
        val db = this.writableDatabase
        db.insert(USER_TABLE_NAME, null, values)
        db.close()
    }

    fun getEasyLevelHighScores():Cursor?{
        val db=this.readableDatabase
        return db.rawQuery("SELECT * FROM "+ USER_TABLE_NAME+" WHERE level='1'"+"  ORDER BY "+ HIGHSCORE+" DESC LIMIT 3",null)
    }
    fun getMediumLevelHighScores():Cursor?{
        val db=this.readableDatabase
        return db.rawQuery("SELECT * FROM "+ USER_TABLE_NAME+" WHERE level='2'"+"  ORDER BY "+ HIGHSCORE+" DESC LIMIT 3",null)
    }
    fun getHighLevelHighScores():Cursor?{
        val db=this.readableDatabase
        return db.rawQuery("SELECT * FROM "+ USER_TABLE_NAME+" WHERE level='3'"+"  ORDER BY "+ HIGHSCORE+" DESC LIMIT 3",null)
    }


    companion object{
        private val DATABASE_NAME = "HighscoresDatabase"
        private val DATABASE_VERSION = 3
        val USER_TABLE_NAME = "Highscores"
        val HIGHSCORE_USERID="user_id"
        val HIGHSCORE="user_highscore"
        val HIGHSCORE_USER_NAME="username"
        val HIGHSCORE_LEVEL="level"



    }
}
