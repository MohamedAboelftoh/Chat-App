package com.example.chatapp.ui.module

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

data class Message(
    var id : String?= null,
    val content : String ?= null,
    val dateTime : Timestamp = Timestamp.now(),
    val senderName : String ?= null,
    val senderId : String ?= null,
    val roomId : String ?= null
){
    fun formatDate():String{
        val formatDate = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatDate.format(dateTime.toDate())
    }
}
