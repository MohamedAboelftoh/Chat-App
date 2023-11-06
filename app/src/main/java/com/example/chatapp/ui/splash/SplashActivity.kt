package com.example.chatapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.chatapp.databinding.ActivitySplashBinding
import com.example.chatapp.ui.Login.LoginActivity
import com.example.chatapp.ui.Register.RegisterActivity

class SplashActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        Handler(Looper.getMainLooper()).postDelayed({
         val intent = Intent(this, RegisterActivity::class.java)
         startActivity(intent)
            finish()
        },2000)
    }
}