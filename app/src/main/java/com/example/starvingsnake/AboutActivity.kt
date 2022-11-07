package com.example.starvingsnake

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val home=findViewById<ImageView>(R.id.imageView3)
        home.setOnClickListener {
            val intent= Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }
    }
}