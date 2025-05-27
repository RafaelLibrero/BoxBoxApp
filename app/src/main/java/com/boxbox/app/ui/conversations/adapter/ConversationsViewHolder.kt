package com.boxbox.app.ui.conversations.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.databinding.ItemConversationBinding
import com.boxbox.app.domain.model.VConversation

class ConversationsViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemConversationBinding.bind(view)

    fun render(conversation: VConversation, onItemSelected: (Int) -> Unit) {
        with (binding) {
            tvTitle.text = conversation.title
            tvEntryCount.text = conversation.entryCount.toString()
            tvCreatedAt.text = conversation.createdAt.toString()
            tvPostCount.text = conversation.postCount.toString()
        }

        itemView.setOnClickListener {
            onItemSelected(conversation.conversationId)
        }
    }
}