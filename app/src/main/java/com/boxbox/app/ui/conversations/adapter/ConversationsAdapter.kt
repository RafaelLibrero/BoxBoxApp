package com.boxbox.app.ui.conversations.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.VConversation

class ConversationsAdapter (
    private var onItemSelected: (Int) -> Unit
) : PagingDataAdapter<VConversation, ConversationsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationsViewHolder {
        return ConversationsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_conversation, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ConversationsViewHolder,
        position: Int
    ) {
        val conversation = getItem(position)
        conversation?.let {
            holder.render(it, onItemSelected)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<VConversation>() {
            override fun areItemsTheSame(oldItem: VConversation, newItem: VConversation): Boolean {
                return oldItem.conversationId == newItem.conversationId
            }

            override fun areContentsTheSame(oldItem: VConversation, newItem: VConversation): Boolean {
                return oldItem == newItem
            }
        }
    }
}