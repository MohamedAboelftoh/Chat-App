package com.example.chatapp.ui.addRoom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityAddRoomBinding
import com.example.chatapp.ui.home.HomeActivity
import org.checkerframework.checker.units.qual.A

class AddRoomActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityAddRoomBinding
    private val viewModule : AddRoomViewModule by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAddRoomBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModule.event.observe(this){
            when(it){
                AddRoomViewEvent.NavigateToHome->{
                    navigateToHome()
                }
            }
        }
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
    private lateinit var categoriesAdapter : AddRoomCategoriesAdapter
    private fun initViews() {
        categoriesAdapter = AddRoomCategoriesAdapter(viewModule.categories)
        viewBinding.vm = viewModule
        viewBinding.lifecycleOwner = this
        viewBinding.content.spinner.adapter = categoriesAdapter
        viewBinding.content.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, itemView: View?, position: Int, id: Long) {
                viewModule.onCategorySelected(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}