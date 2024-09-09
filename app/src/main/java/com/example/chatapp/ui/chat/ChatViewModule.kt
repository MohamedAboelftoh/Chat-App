package com.example.chatapp.ui.chat

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.example.chatapp.ui.firestore.MessageDao
import com.example.chatapp.ui.module.Message
import com.example.chatapp.ui.module.Room
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.toObject

class ChatViewModule:ViewModel() {
    private lateinit var context: Context
    fun getContext(mContext: Context){
        context = mContext
    }
      var room: Room? = null
    var messageLiveData = MutableLiveData<String>()
    var newMessageLiveData = SingleLiveEvent<List<Message>>()
    fun changeRoom(room: Room?){
        if (room != null && room.id!!.isNotEmpty()) {
            listenForMessageInRoom(room)
        } else {
            // Handle the case where the room object or its ID is null or empty
        }
    }
    private fun listenForMessageInRoom(room : Room){
        MessageDao.getMessageCollection(room.id ?:"")
            .addSnapshotListener(EventListener { snapShot, error ->
                val newMessages = mutableListOf<Message>()
                snapShot?.documentChanges?.forEach {docChange->
                     if(docChange.type == DocumentChange.Type.ADDED){
                         val message = docChange.document.toObject(Message::class.java)
                          newMessages.add(message)
                     }
                    else if(docChange.type == DocumentChange.Type.MODIFIED){

                     }
                     else if(docChange.type == DocumentChange.Type.REMOVED){

                     }
                }
                newMessageLiveData.value= newMessages
            })
    }
    @SuppressLint("SuspiciousIndentation")
    fun sendMessage(){
    val message = Message(
        content = messageLiveData.value,
        senderName = SessionProvider.user?.userName,
        senderId = SessionProvider.user?.id,
        roomId = room?.id
    )
        MessageDao.sendMessage(message){task->
            if(messageLiveData.value.isNullOrBlank())return@sendMessage
            if(task.isSuccessful){
                messageLiveData.value = ""
                return@sendMessage
            }
            val dialog = AlertDialog.Builder(context)
            dialog.setMessage("Something went error,please try again latter")
                .setPositiveButton("ok"){p0,p1->
                    p0.dismiss()
                }
                .show()
        }
    }
}