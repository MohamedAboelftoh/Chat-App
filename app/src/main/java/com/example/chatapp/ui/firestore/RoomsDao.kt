package com.example.chatapp.ui.firestore

import com.example.chatapp.ui.module.Room
import com.example.chatapp.ui.module.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore

object RoomsDao {
     fun getRoomsCollection(): CollectionReference {
        val database = Firebase.firestore
        return database.collection("Rooms")
    }
    fun createRoom(
        title : String,
        desc : String,
        categoryId : Int,
        ownerId : String,
        onCompleteListener: OnCompleteListener<Void>){
        val collection = getRoomsCollection()
        val docRef = collection.document()
        val room = Room(id =docRef.id,title = title , description = desc , categoryId = categoryId ,ownerId = ownerId)
        docRef.set(room)
            .addOnCompleteListener(onCompleteListener)
    }

    fun getAllRooms(onCompleteListener: OnCompleteListener<QuerySnapshot>) {
        getRoomsCollection().get().addOnCompleteListener(onCompleteListener)
    }
}