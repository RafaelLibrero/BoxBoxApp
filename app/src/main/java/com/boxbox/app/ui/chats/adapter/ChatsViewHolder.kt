package com.boxbox.app.ui.chats.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.databinding.ItemChatBinding
import com.boxbox.app.domain.model.Chat

class ChatsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemChatBinding.bind(view)

    fun render(chat: Chat, onItemSelected: (Int) -> Unit) {
        with(binding) {
            tvUsername.text = chat.user2Id.toString()
        }
    }
}