package com.example.chatapp.ui.register

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.example.chatapp.ui.firestore.UsersDao
import com.example.chatapp.ui.module.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class RegisterViewModule: ViewModel() {
   @SuppressLint("StaticFieldLeak")
   private lateinit var context: Context
   fun getContext(mContext: Context){
      context = mContext
   }

   var userName = MutableLiveData<String>()
   var password = MutableLiveData<String>()
   var email = MutableLiveData<String>()
   var userNameError = MutableLiveData<String?>()
   var passwordError = MutableLiveData<String?>()
   var emailError = MutableLiveData<String?>()
   val events = SingleLiveEvent<RegisterViewEvent>()
   private val auth = Firebase.auth
   fun register(){
     if(!validation()) return
      auth.createUserWithEmailAndPassword(email.value!!,password.value!!).addOnCompleteListener{task->
         if(task.isSuccessful){
            insertUserToFireStore(task.result.user?.uid)
         }else{
            val dialog = AlertDialog.Builder(context)
            dialog.setMessage(task.exception?.localizedMessage).show()
         }
      }
   }

   private fun insertUserToFireStore(uid: String?) {
      val user = User(id = uid,
         userName = userName.value,
        email = email.value
      )
       UsersDao.createUser(user) { task ->
        if(task.isSuccessful){
           val dialog = AlertDialog.Builder(context)
           dialog.setMessage("user added successfully")
              .setPositiveButton("ok") { p0, p1 ->
                 SessionProvider.user = user
                 events.postValue(RegisterViewEvent.NavigateToHome)
                 // save user
                 // navigate to home
              }
              .show()
        }else{
           val dialog = AlertDialog.Builder(context)
           dialog.setMessage(task.exception?.localizedMessage).show()
        }
       }
   }

   private fun validation(): Boolean {
      var isValid = true
       if(userName.value.isNullOrBlank()){
          userNameError.postValue("Please Enter User Name")
          isValid = false
       }else{
          userNameError.postValue(null)
       }
      if(password.value.isNullOrBlank()){
         passwordError.postValue("Please Enter Password")
         isValid = false
      }else{
         passwordError.postValue(null)
      }
      if(email.value.isNullOrBlank()){
         emailError.postValue("Please Enter User Email")
         isValid = false
      }else{
         emailError.postValue(null)
      }
      return isValid
   }
   fun navigateToLogin(){
      events.postValue(RegisterViewEvent.NavigateToLogin)
   }
}