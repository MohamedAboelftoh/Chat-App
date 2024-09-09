package com.example.chatapp.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.example.chatapp.ui.firestore.UsersDao
import com.example.chatapp.ui.module.User
import com.example.chatapp.ui.register.RegisterViewEvent
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModule : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    private lateinit var context: Context
    fun getContext(mContext: Context) {
        context = mContext
    }

    var password = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var passwordError = MutableLiveData<String?>()
    var emailError = MutableLiveData<String?>()
    val events = SingleLiveEvent<LoginViewEvent>()
    private val auth = Firebase.auth
    fun register() {
        if (!validation()) return
        auth.signInWithEmailAndPassword(email.value!!, password.value!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    getUserFromFireStore(task.result.user?.uid)
                } else {
                    val dialog = AlertDialog.Builder(context)
                    dialog.setMessage(task.exception?.localizedMessage).show()
                }
            }
    }

    private fun getUserFromFireStore(uid: String?) {
        UsersDao.getUser(uid) { task ->
            if (task.isSuccessful) {
                val user = task.result.toObject(User::class.java)
                SessionProvider.user = user
                val dialog = AlertDialog.Builder(context)
                dialog.setMessage("logged in successfully")
                    .setPositiveButton("ok") { p0, p1 ->
                        SessionProvider.user = user
                        events.postValue(LoginViewEvent.NavigateToHome)
                        // save user
                        // navigate to home
                    }
                    .show()

            } else {
                val dialog = AlertDialog.Builder(context)
                dialog.setMessage(task.exception?.localizedMessage).show()
            }

        }
    }


    private fun validation(): Boolean {
        var isValid = true
        if (password.value.isNullOrBlank()) {
            passwordError.postValue("Please Enter Password")
            isValid = false
        } else {
            passwordError.postValue(null)
        }
        if (email.value.isNullOrBlank()) {
            emailError.postValue("Please Enter User Email")
            isValid = false
        } else {
            emailError.postValue(null)
        }
        return isValid
    }

    fun navigateRegister() {
        events.postValue(LoginViewEvent.NavigateToRegister)
    }
}