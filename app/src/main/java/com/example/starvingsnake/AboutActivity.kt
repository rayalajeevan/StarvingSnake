package com.example.starvingsnake

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

class AboutActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val home=findViewById<ImageView>(R.id.imageView3)
        val about=findViewById<TextView>(R.id.about)
        val frame=findViewById<RelativeLayout>(R.id.relative_about)
        home.setOnClickListener {
            val intent= Intent(this,HomePageActivity::class.java)
            startActivity(intent)
        }
        val ani_slide_up=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.shake)
        for(i in 0..frame.childCount-1){
            val x=frame.getChildAt(i)
            x.startAnimation(ani_slide_up)
        }
        val ani_slide=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.animator.slide)
        home.startAnimation(ani_slide)
        about.startAnimation(ani_slide)


    }
}