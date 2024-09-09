package com.example.chatapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.login.LoginActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityRegisterBinding
    private lateinit var viewModule: RegisterViewModule
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
         auth = Firebase.auth
        WindowCompat.setDecorFitsSystemWindows(window,false)
        initViews()
        bindObserves()
    }
    private fun reload() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }
    private fun bindObserves() {
        viewModule.events.observe(this) {
         when(it){
             RegisterViewEvent.NavigateToHome ->{
                 navigateToHome()
             }
             RegisterViewEvent.NavigateToLogin ->{
                 navigateToLogin()
             }
         }
        }
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

    override fun onStart() {
        super.onStart()
        viewModule.getContext(this)
    }

    private fun initViews() {
        viewModule = ViewModelProvider(this)[RegisterViewModule::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModule
    }
}