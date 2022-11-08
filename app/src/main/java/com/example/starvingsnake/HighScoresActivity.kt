package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.bottomnavigation.BottomNavigationView

class HighScoresActivity : AppCompatActivity() {
    private  lateinit var highscore_text:TextView
    private lateinit var frame_btn:RelativeLayout
    private  lateinit var player_1_username:TextView
    private  lateinit var player_2_username:TextView
    private  lateinit var player_3_username:TextView
    private  lateinit var player_1_score:TextView
    private  lateinit var player_2_score:TextView
    private  lateinit var player_3_score:TextView
    @SuppressLint("Range", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)
        highscore_text=findViewById<TextView>(R.id.textViewhigh)
        frame_btn=findViewById<RelativeLayout>(R.id.highscores_frame)
        player_1_username=findViewById<TextView>(R.id.player_1_username)
        player_2_username=findViewById<TextView>(R.id.player_2_username)
        player_3_username=findViewById<TextView>(R.id.player_3_username)
        player_1_score=findViewById<TextView>(R.id.player_1_score)
        player_2_score=findViewById<TextView>(R.id.player_2_score)
        player_3_score=findViewById<TextView>(R.id.player_3_score)
        var db=DatabaseAdapter(this,null)
        var cursor=db.getEasyLevelHighScores()
        if (cursor != null) {
            switchScores(cursor)
        }
        val navigation=findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.easy_btn->{
                    cursor=db.getEasyLevelHighScores()
                    cursor?.let { it1 -> switchScores(it1) }
                }
                R.id.medium_btn->{
                    cursor=db.getMediumLevelHighScores()
                    cursor?.let { it1 -> switchScores(it1) }

                }
                R.id.high_btn->{
                    cursor=db.getHighLevelHighScores()
                    cursor?.let { it1 -> switchScores(it1) }

                }

            }
            return@setOnNavigationItemSelectedListener true
        }
        player_2_username.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Username",player_2_username.text.toString())
            intent.putExtra("level",1)
            startActivity(intent)
        }
        player_1_username.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Username",player_1_username.text.toString())
            intent.putExtra("level",1)
            startActivity(intent)
        }
        player_3_username.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Username",player_3_username.text.toString())
            intent.putExtra("level",1)
            startActivity(intent)
        }
        player_2_score.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Username",player_2_username.text.toString())
            intent.putExtra("level",1)
            startActivity(intent)
        }
        player_1_score.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Username",player_1_username.text.toString())
            intent.putExtra("level",1)
            startActivity(intent)
        }
        player_3_score.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Username",player_3_username.text.toString())
            intent.putExtra("level",1)
            startActivity(intent)
        }


    }
    @SuppressLint("Range", "ResourceType")
    fun switchScores(cursor:Cursor){
        updateScpores()
        if (cursor?.moveToFirst()==true) {
            player_1_username.setText(cursor?.getString(cursor!!.getColumnIndex(DatabaseAdapter.HIGHSCORE_USER_NAME)))
            player_1_score.setText(
                cursor?.getInt(
                    cursor!!.getColumnIndex(
                        DatabaseAdapter.HIGHSCORE
                    )
                ).toString()
            )
        }
        if (cursor?.moveToNext()==true) {
            player_2_username.setText(cursor?.getString(cursor.getColumnIndex(DatabaseAdapter.HIGHSCORE_USER_NAME)))
            player_2_score.setText(
                cursor?.getInt(
                    cursor!!.getColumnIndex(
                        DatabaseAdapter.HIGHSCORE
                    )
                ).toString()
            )
        }
        if (cursor?.moveToNext()==true) {
            player_3_username.setText(cursor?.getString(cursor.getColumnIndex(DatabaseAdapter.HIGHSCORE_USER_NAME)))
            player_3_score.setText(
                cursor?.getInt(
                    cursor!!.getColumnIndex(
                        DatabaseAdapter.HIGHSCORE
                    )
                ).toString()
            )
        }
        val ani_slide=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.slide)
        val ani_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.slide_up)
        frame_btn.startAnimation(ani_slide_up)
        highscore_text.startAnimation(ani_slide)
        player_1_username.startAnimation(ani_slide_up)
        player_1_score.startAnimation(ani_slide_up)
        player_2_username.startAnimation(ani_slide_up)
        player_2_score.startAnimation(ani_slide_up)
    }
    fun updateScpores(){
        player_1_username.text=""
        player_2_username.text=""
        player_3_username.text=""
        player_1_score.text=""
        player_2_score.text=""
        player_3_score.text=""
    }
}