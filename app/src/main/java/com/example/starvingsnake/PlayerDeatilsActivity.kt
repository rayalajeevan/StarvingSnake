package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PlayerDeatilsActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_deatils)
        val submit_btn=findViewById<Button>(R.id.submit)
        val player_name=findViewById<EditText>(R.id.player_name)
        val back_btn=findViewById<ImageView>(R.id.back_button_on_playerdeatils)
        submit_btn.setOnClickListener {
            if (player_name.text.toString().trim()!="") {
                val intent = Intent(this, GameLevelsActivity::class.java)
                intent.putExtra("Username", player_name.text.toString())
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"Invalid Player Name",Toast.LENGTH_SHORT).show()
            }
        }
        back_btn.setOnClickListener {
            onBackPressed()
        }
        val ani_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.slide_up)
        back_btn.startAnimation(ani_slide_up)
        submit_btn.startAnimation(ani_slide_up)
        player_name.startAnimation(ani_slide_up)
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }


}