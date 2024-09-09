package com.example.chatapp.ui.addRoom

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.common.SingleLiveEvent
import com.example.chatapp.ui.firestore.RoomsDao
import com.example.chatapp.ui.module.Category

class AddRoomViewModule : ViewModel() {
    private lateinit var context: Context
    fun getContext(mContext: Context){
        context = mContext
    }
    val event = SingleLiveEvent<AddRoomViewEvent>()
    val roomName = MutableLiveData<String?>()
    val roomNameError = MutableLiveData<String?>()
    val roomDescription = MutableLiveData<String?>()
    val roomDescriptionError = MutableLiveData<String?>()
    val categories = Category.getCategories()
    private var selectedCategory = categories[0]
    fun createRoom(){
        if(!validation())return
        RoomsDao.createRoom(
            title = roomName.value?:"",
            desc = roomDescription.value?:"",
            categoryId = selectedCategory.id,
            ownerId = SessionProvider.user?.id?:""
        ){task->
            if (task.isSuccessful){
                val dialog = AlertDialog.Builder(context)
                dialog.setMessage("Room created successfully")
                    .setPositiveButton("ok") { p0, p1 ->
                        event.postValue(AddRoomViewEvent.NavigateToHome)
                    }
                    .show()
            }else{
                val dialog = AlertDialog.Builder(context)
                dialog.setMessage("something went error")
                    .setPositiveButton("ok") { p0, p1 ->
                        p0.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        if(roomName.value.isNullOrBlank()){
            roomNameError.postValue("Room Name Required")
            isValid = false
        }else{
            roomNameError.postValue(null)
        }
        if(roomDescription.value.isNullOrBlank()){
            roomDescriptionError.postValue("Room Description Required")
            isValid = false
        }else{
            roomDescriptionError.postValue(null)
        }
        return isValid
    }

    fun onCategorySelected(position: Int) {
        selectedCategory = categories[position]
    }
}