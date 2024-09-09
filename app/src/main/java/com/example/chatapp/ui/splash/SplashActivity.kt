package com.example.chatapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.chatapp.databinding.ActivitySplashBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity
import com.example.chatapp.ui.register.RegisterActivity

class SplashActivity : AppCompatActivity() {
    val viewModule : SplashViewModule by viewModels()
    private lateinit var viewBinding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        subscribeToLiveData()
        Handler(Looper.getMainLooper()).postDelayed({
            viewModule.redirect()
        },2000)
    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun subscribeToLiveData() {
        viewModule.event.observe(this){
            when(it){
                SplashViewEvent.NavigateToHome->{
                    navigateToHome()
                }
                SplashViewEvent.NavigateToLogin->{
                    navigateToLogin()
                }
            }
        }
    }
}