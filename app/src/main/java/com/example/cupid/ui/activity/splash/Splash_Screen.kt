package com.example.cupid.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.cupid.R
import com.example.cupid.ui.activity.Onboarding.Welcome_Screen

class Splash_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.hide()

        Handler().postDelayed({
            val intent= Intent(this@Splash_Screen, Welcome_Screen::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}