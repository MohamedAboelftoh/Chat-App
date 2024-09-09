package com.example.chatapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityLoginBinding
import com.example.chatapp.databinding.ActivityRegisterBinding
import com.example.chatapp.ui.home.HomeActivity
import com.example.chatapp.ui.register.RegisterActivity
import com.example.chatapp.ui.register.RegisterViewEvent
import com.example.chatapp.ui.register.RegisterViewModule

class LoginActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityLoginBinding
    private lateinit var viewModule: LoginViewModule
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        initViews()
        bindObserves()
    }

    private fun bindObserves() {
        viewModule.events.observe(this) {
            when (it) {
                LoginViewEvent.NavigateToHome -> {
                    navigateToHome()
                }

                LoginViewEvent.NavigateToRegister -> {
                    navigateToRegister()
                }
            }
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
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
        viewModule = ViewModelProvider(this)[LoginViewModule::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModule
    }
}