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
        new_game.setOnClickListener {
        val intent=Intent(this,PlayerDeatilsActivity::class.java)
            startActivity(intent)
        }
    }
}