package com.example.chatapp.ui.chat

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.databinding.ActivityChatBinding
import com.example.chatapp.ui.module.Room

class ChatActivity : AppCompatActivity() {
    private val viewModule:ChatViewModule by viewModels()
    private lateinit var viewBinding : ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        initParam()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModule.newMessageLiveData.observe(this){
            messagesAdapter.addNewMessage(it)
            viewBinding.content.messageRecyclerView.scrollToPosition(messagesAdapter.itemCount)
        }

    }

    private fun initParam() {
        val room = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){
            intent.getParcelableExtra("room",Room::class.java)
            }else{
            intent.getParcelableExtra("room") as Room?
        }
       //viewModule................................................
        viewModule.room = room
        viewModule.changeRoom(room)
    }
    override fun onStart() {
        super.onStart()
        viewModule.getContext(this)
    }
private val messagesAdapter = MessagesAdapter(mutableListOf())
    private fun initViews() {
        viewBinding.vm = viewModule
        viewBinding.lifecycleOwner = this
        viewBinding.content.messageRecyclerView.adapter = messagesAdapter
        (viewBinding.content.messageRecyclerView.layoutManager as LinearLayoutManager)
            .stackFromEnd
    }
}