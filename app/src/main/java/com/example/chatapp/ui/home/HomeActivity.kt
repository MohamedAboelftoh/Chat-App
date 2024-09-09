package com.example.chatapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.chatapp.databinding.ActivityHomeBinding
import com.example.chatapp.ui.addRoom.AddRoomActivity
import com.example.chatapp.ui.chat.ChatActivity
import com.example.chatapp.ui.login.LoginActivity
import com.example.chatapp.ui.module.Room

class HomeActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityHomeBinding
    //دي طريقه فقط عشان تعرف ال viewModule
    val viewModule : HomeViewModule by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
       viewModule.event.observe(this
       ) {
           when(it){
               HomeViewEvent.NavigateToAddRoom->{
                   navigateToAddRoom()
               }
               HomeViewEvent.NavigateToLogin->{
                   navigateToLogin()
               }

               else -> {}
           }
       }
        viewModule.roomsLiveData.observe(this){
            roomRecyclerAdapter.changeData(it)
        }
    }
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToAddRoom() {
        val intent= Intent(this,AddRoomActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        viewModule.loadRooms()
        viewModule.getContext(this)
    }

private val roomRecyclerAdapter = RoomRecyclerAdapter()
    private fun initViews() {
      viewBinding.vm = viewModule
        viewBinding.lifecycleOwner = this
        viewBinding.content.roomRecyclerView.adapter = roomRecyclerAdapter
        roomRecyclerAdapter.onItemClickListener = RoomRecyclerAdapter.OnItemClickListener{position, room ->  
            navigateToChat(room)
        }
    }

    private fun navigateToChat(room:Room) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("room",room)
        startActivity(intent)
    }
}
