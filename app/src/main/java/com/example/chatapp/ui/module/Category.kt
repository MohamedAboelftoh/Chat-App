package com.example.chatapp.ui.module

import com.example.chatapp.R

data class Category(
    var id:Int,
    var title:String,
    var img:Int
    ){
    companion object{
        fun getCategories()=
            listOf<Category>(
                Category(1,"Sports", R.drawable.sports),
                Category(2,"Music", R.drawable.music),
                Category(3,"Movies", R.drawable.movies)
                )
        fun getCategoryImageByCategoryId(catId : Int?):Int{
            return when(catId){
                1-> R.drawable.sports
                2-> R.drawable.music
                3-> R.drawable.movies
                else -> R.drawable.sports
            }
        }
    }
}
