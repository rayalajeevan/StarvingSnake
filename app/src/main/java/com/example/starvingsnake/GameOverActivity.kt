package com.example.starvingsnake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class GameOverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        val game_score=intent.getIntExtra("Game_score",0)
        val user_name=intent.getStringExtra("Username")
        val level=intent.getIntExtra("level",0)
        var home_btn=findViewById(R.id.imageView1) as ImageView
        val text_game=findViewById<TextView>(R.id.scoreview_ingameover)
        text_game.setText("Your Score is "+game_score.toString())
        val try_gain_ntn=findViewById(R.id.button) as Button
        home_btn.setOnClickListener {
            val intent=Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }
        try_gain_ntn.setOnClickListener {
            val intent=Intent(this,PlayingAreaActivity::class.java)
            intent.putExtra("Game_score",game_score)
            intent.putExtra("level",level)
            intent.putExtra("Username",user_name)
            startActivity(intent)

        }
    }
}