package com.boxbox.app.ui.conversations.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.VConversation

class ConversationsAdapter (
    private var conversationsList: List<VConversation> = mutableListOf(),
    private var onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<ConversationsViewHolder>() {

    fun updateList(list: List<VConversation>) {
        conversationsList = list
        notifyDataSetChanged()
    }

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
        holder.render(conversationsList[position], onItemSelected)
    }

    override fun getItemCount() = conversationsList.size
}