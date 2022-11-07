package com.example.starvingsnake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomePageActivity : AppCompatActivity() {
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
    }
}