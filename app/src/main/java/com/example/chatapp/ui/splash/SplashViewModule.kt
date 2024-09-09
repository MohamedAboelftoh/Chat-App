package com.example.chatapp.ui.splash

import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.example.chatapp.ui.firestore.UsersDao
import com.example.chatapp.ui.module.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SplashViewModule:ViewModel() {
    val event = SingleLiveEvent<SplashViewEvent>()
    fun redirect() {
        if(Firebase.auth.currentUser == null){
            event.postValue(SplashViewEvent.NavigateToLogin)
            return
        }
        UsersDao.getUser(
            Firebase.auth.currentUser?.uid?:""
        ){task->
            if(!task.isSuccessful){
                event.postValue(SplashViewEvent.NavigateToLogin)
                return@getUser
            }
            val user = task.result.toObject(User::class.java)
            SessionProvider.user = user
            event.postValue(SplashViewEvent.NavigateToHome)
        }
    }

}