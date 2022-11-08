package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class HomePageActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        val new_game=findViewById<Button>(R.id.new_game)
        val scores=findViewById<Button>(R.id.highscores)
        val about=findViewById<Button>(R.id.about)
        new_game.setOnClickListener {
        val intent=Intent(this,PlayerDeatilsActivity::class.java)
            startActivity(intent)
        }
        scores.setOnClickListener {
            val intent=Intent(this,HighScoresActivity::class.java)
            startActivity(intent)
        }
        about.setOnClickListener {
            val intent=Intent(this,AboutActivity::class.java)
            startActivity(intent)
        }
        val left_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.left_to_right_slide)
        val right_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.right_to_left)
        new_game.startAnimation(left_slide_up)
        scores.startAnimation(left_slide_up)
        about.startAnimation(left_slide_up)
        val image=findViewById<ImageView>(R.id.imageView2)
        image.startAnimation(right_slide_up)
    }
}