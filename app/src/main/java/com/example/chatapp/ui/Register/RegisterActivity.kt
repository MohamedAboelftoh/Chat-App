package com.example.chatapp.ui.Register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityRegisterBinding
    private lateinit var viewModule: RegisterViewModule
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
    }

    private fun initViews() {
        viewModule = ViewModelProvider(this)[RegisterViewModule::class.java]
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModule
    }
}