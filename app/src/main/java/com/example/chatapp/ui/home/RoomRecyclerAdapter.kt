package com.example.chatapp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.chatapp.databinding.RoomItemBinding
import com.example.chatapp.ui.module.Category
import com.example.chatapp.ui.module.Room

class RoomRecyclerAdapter (var rooms : List<Room>? = listOf()): Adapter<RoomRecyclerAdapter.ViewHolder>() {
    class ViewHolder(private val itemBinding : RoomItemBinding) : RecyclerView.ViewHolder(itemBinding.root){
        fun bind(room : Room?){
            itemBinding.imageCategory.setImageResource(Category.getCategoryImageByCategoryId(room?.categoryId))
            itemBinding.title.text = room?.title
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val viewBinding = RoomItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
      return rooms?.size?:0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(rooms?.get(position))
        onItemClickListener.let {
            holder.itemView.setOnClickListener { view->
                it?.onItemClick(position,rooms!![position])
            }
        }
    }

    fun changeData(rooms: List<Room>?) {
        this.rooms = rooms
        notifyDataSetChanged()
    }
    var onItemClickListener : OnItemClickListener?=null
    fun interface OnItemClickListener{
        fun onItemClick(position: Int,room:Room)
    }
}