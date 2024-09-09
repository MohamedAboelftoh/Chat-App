package com.example.chatapp.ui.firestore

import com.example.chatapp.ui.module.Message
import com.google.android.gms.tasks.OnCompleteListener

object MessageDao {
    fun getMessageCollection(roomId:String) =
        RoomsDao.getRoomsCollection()
            .document(roomId)
            .collection("messages")
    fun sendMessage(message: Message,onCompleteListener: OnCompleteListener<Void>){
      val messageDoc = getMessageCollection(message.roomId?:"")
          .document()
        message.id = messageDoc.id
        messageDoc.set(message)
            .addOnCompleteListener(onCompleteListener)
    }
}
