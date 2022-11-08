package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        val game_score=intent.getIntExtra("Game_score",0)
        val user_name=intent.getStringExtra("Username")
        val level=intent.getIntExtra("level",0)
        val text_game=findViewById<TextView>(R.id.scoreview_ingameover)
        text_game.setText("Your Score is "+game_score.toString())
        val try_gain_ntn=findViewById(R.id.button) as Button
        try_gain_ntn.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Game_score",game_score)
            intent.putExtra("level",level)
            intent.putExtra("Username",user_name)
            startActivity(intent)
            finish()

        }
        var game=findViewById(R.id.game_1) as TextView
        val over=findViewById<TextView>(R.id.game2)
        val ani_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.shake)
        game.startAnimation(ani_slide_up)
        over.startAnimation(ani_slide_up)
        text_game.startAnimation(ani_slide_up)
        try_gain_ntn.startAnimation(ani_slide_up)

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
}