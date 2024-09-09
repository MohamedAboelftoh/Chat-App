package com.example.chatapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.example.chatapp.ui.firestore.RoomsDao
import com.example.chatapp.ui.module.Room
import com.example.chatapp.ui.register.RegisterViewEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class HomeViewModule:ViewModel() {
    private lateinit var context: Context
    fun getContext(mContext: Context){
        context = mContext
    }
     val event = SingleLiveEvent<HomeViewEvent>()
    val roomsLiveData = MutableLiveData<List<Room>>()
    fun navigateToAddRoom(){
        event.postValue(HomeViewEvent.NavigateToAddRoom)
    }

    @SuppressLint("SuspiciousIndentation")
    fun loadRooms() {
        RoomsDao.getAllRooms(){task->
            if (!task.isSuccessful){
                //show message
                return@getAllRooms
            }
          val rooms = task.result.toObjects(Room::class.java)
            roomsLiveData.postValue(rooms)
        }
    }
    fun logout(){
        val dialog = AlertDialog.Builder(context)
        dialog.setMessage("Are you sure want to logout?")
            .setPositiveButton("yes") { p0, p1 ->
                Firebase.auth.signOut()
                SessionProvider.user = null
                event.postValue(HomeViewEvent.NavigateToLogin)
            }
            .setNegativeButton("No"){p0,p1 ->
                p0.dismiss()
            }
            .show()

    }
}