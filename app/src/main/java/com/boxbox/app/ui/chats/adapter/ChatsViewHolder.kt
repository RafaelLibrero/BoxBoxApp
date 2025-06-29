package com.boxbox.app.ui.chats.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.boxbox.app.databinding.ItemChatBinding
import com.boxbox.app.domain.model.ChatWithUser
import com.boxbox.app.utils.DateFormatter

class ChatsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemChatBinding.bind(view)

    fun render(chatWithUser: ChatWithUser, onItemSelected: (Int) -> Unit) {
        val chat = chatWithUser.chatSummary
        val user = chatWithUser.user
        with(binding) {
            tvUsername.text = user.userName
            ivAvatar.load(user.profilePicture) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
            tvLastMessage.text = chat.lastMessage?.content ?: ""
            tvTimestamp.text = chat.lastMessage?.let { DateFormatter.getLastMessageDate(it.sentAt) }
        }
    }
}