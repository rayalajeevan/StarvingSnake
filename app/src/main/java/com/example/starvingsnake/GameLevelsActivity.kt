package com.example.starvingsnake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class GameLevelsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_levels)
        val user_name=intent.getStringExtra("Username").toString()
        val high_btn=findViewById<Button>(R.id.high_btn)
        val med_btn=findViewById<Button>(R.id.med_btn)
        val easy_btn=findViewById<Button>(R.id.easy_btn)
        high_btn.setOnClickListener {
            val intent = Intent(this, PlayingAreaActivity::class.java)
            intent.putExtra("Username",user_name)
            intent.putExtra("level",3)
            startActivity(intent)
            finish()
        }
        med_btn.setOnClickListener {
            val intent = Intent(this, PlayingAreaActivity::class.java)
            intent.putExtra("Username",user_name)
            intent.putExtra("level",2)
            startActivity(intent)
            finish()
        }
        easy_btn.setOnClickListener {
            val intent = Intent(this, PlayingAreaActivity::class.java)
            intent.putExtra("Username",user_name)
            intent.putExtra("level",1)
            startActivity(intent)
            finish()
        }
        val ani_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.left_to_right_slide)
        easy_btn.startAnimation(ani_slide_up)
        high_btn.startAnimation(ani_slide_up)
        med_btn.startAnimation(ani_slide_up)
    }
}