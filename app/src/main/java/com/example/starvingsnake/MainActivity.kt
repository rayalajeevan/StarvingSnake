package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import com.example.starvingsnake.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val imaege=findViewById<ImageView>(R.id.app_lgo_imae)
        val ani_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.zoom)
        imaege.startAnimation(ani_slide_up)

        Handler().postDelayed(Runnable {
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}


