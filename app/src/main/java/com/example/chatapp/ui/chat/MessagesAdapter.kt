package com.example.chatapp.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatapp.databinding.ReciveMessageBinding
import com.example.chatapp.databinding.SentMessageBinding
import com.example.chatapp.ui.SessionProvider
import com.example.chatapp.ui.module.Message

class MessagesAdapter(val messages : MutableList<Message>):Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       if(viewType == MessageType.Sent.value){
           val itemBinding = SentMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
           return SentMessageViewHolder(itemBinding)
       }else{
           val itemBinding = ReciveMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
           return ReceiveMessageViewHolder(itemBinding)
       }
    }

    override fun getItemCount(): Int {
       return messages.size
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        if(message.senderId == SessionProvider.user?.id){
          return MessageType.Sent.value
        }else{
            return MessageType.Received.value
        }
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       when(holder){
           is SentMessageViewHolder->{
               holder.bind(messages[position])
           }
           is ReceiveMessageViewHolder ->{
               holder.bind(messages[position])
           }
       }
    }

    fun addNewMessage(newMessage: List<Message>?) {
            if(newMessage == null)return
        messages.addAll(newMessage)
         val oldSize = messages.size
        notifyItemRangeInserted(oldSize,newMessage.size)
    }
}
enum class MessageType(val value :Int){
    Received(200) ,
    Sent(100)
}
class SentMessageViewHolder(private val itemBinding : SentMessageBinding):ViewHolder(itemBinding.root){
    fun bind(message: Message){
        itemBinding.setMessage(message)
        itemBinding.executePendingBindings()
    }
}
class ReceiveMessageViewHolder(private val itemBinding : ReciveMessageBinding):ViewHolder(itemBinding.root){
    fun bind(message: Message){
        itemBinding.setMessage(message)
        itemBinding.executePendingBindings()
    }
}
