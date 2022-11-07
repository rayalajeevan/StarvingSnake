package com.example.starvingsnake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class PlayerDeatilsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_deatils)
        val submit_btn=findViewById<Button>(R.id.submit)
        val player_name=findViewById<EditText>(R.id.player_name)
        submit_btn.setOnClickListener {
            val intent=Intent(this,GameLevelsActivity::class.java)
            intent.putExtra("Username",player_name.text.toString())
            startActivity(intent)
        }
    }
}