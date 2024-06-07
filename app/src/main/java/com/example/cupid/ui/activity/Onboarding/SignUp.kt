package com.example.cupid.ui.activity.Onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cupid.R

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_sign_up)
        val next:Button=findViewById(R.id.next)
        next.setOnClickListener {
            val intent= Intent(this@SignUp,add_email::class.java)
            startActivity(intent)
        }
    }
}