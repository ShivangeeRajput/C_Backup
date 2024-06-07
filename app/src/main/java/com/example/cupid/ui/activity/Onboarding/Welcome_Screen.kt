package com.example.cupid.ui.activity.Onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cupid.R

class Welcome_Screen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        supportActionBar?.hide()
        val btnLogin:Button=findViewById(R.id.btnLogin)
        val btnSignUp:Button=findViewById(R.id.btnSignUp)

        btnLogin.setOnClickListener {
            val intent=Intent(this@Welcome_Screen,SignIn::class.java)
            startActivity(intent)
        }
        btnSignUp.setOnClickListener {
            val intent=Intent(this@Welcome_Screen,SignUp::class.java)
            startActivity(intent)
        }
    }
}