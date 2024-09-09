package com.example.chatapp.ui.module

import android.os.Parcelable
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Parcelize
data class Room(
    val id : String ?= null,
    val title : String ?= null ,
    val description : String ?= null ,
    val categoryId : Int ?= null ,
    val ownerId : String ?= null
):Parcelable