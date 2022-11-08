package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import com.google.android.material.animation.AnimationUtils

class HighScoresActivity : AppCompatActivity() {
    @SuppressLint("Range", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_scores)
        val home_btn=findViewById<ImageView>(R.id.imageView3)
        val highscore_text=findViewById<TextView>(R.id.textViewhigh)
        val frame_btn=findViewById<RelativeLayout>(R.id.highscores_frame)
        var player_1_username=findViewById<TextView>(R.id.player_1_username)
        var player_2_username=findViewById<TextView>(R.id.player_2_username)
        var player_3_username=findViewById<TextView>(R.id.player_3_username)
        var player_1_score=findViewById<TextView>(R.id.player_1_score)
        var player_2_score=findViewById<TextView>(R.id.player_2_score)
        var player_3_score=findViewById<TextView>(R.id.player_3_score)
        var db=DatabaseAdapter(this,null)
        var cursor=db.getHighScores()
        if (cursor?.moveToFirst()==true) {
            player_1_username.setText(cursor?.getString(cursor.getColumnIndex(DatabaseAdapter.HIGHSCORE_USER_NAME)))
            player_1_score.setText(
                 cursor?.getInt(
                    cursor?.getColumnIndex(
                        DatabaseAdapter.HIGHSCORE
                    )
                ).toString()
            )
        }
            if (cursor?.moveToNext()==true) {
            player_2_username.setText(cursor?.getString(cursor.getColumnIndex(DatabaseAdapter.HIGHSCORE_USER_NAME)))
            player_2_score.setText(
                 cursor?.getInt(
                    cursor?.getColumnIndex(
                        DatabaseAdapter.HIGHSCORE
                    )
                ).toString()
            )
        }
        if (cursor?.moveToNext()==true) {
            player_3_username.setText(cursor?.getString(cursor.getColumnIndex(DatabaseAdapter.HIGHSCORE_USER_NAME)))
            player_3_score.setText(
                 cursor?.getInt(
                    cursor?.getColumnIndex(
                        DatabaseAdapter.HIGHSCORE
                    )
                ).toString()
            )
        }
        home_btn.setOnClickListener {
            val intent=Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }
        val ani_slide=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.slide)
        val ani_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.slide_up)
        frame_btn.startAnimation(ani_slide_up)
        home_btn.startAnimation(ani_slide)
        highscore_text.startAnimation(ani_slide)
        player_1_username.startAnimation(ani_slide_up)
        player_1_score.startAnimation(ani_slide_up)
        player_2_username.startAnimation(ani_slide_up)
        player_2_score.startAnimation(ani_slide_up)

    }
}