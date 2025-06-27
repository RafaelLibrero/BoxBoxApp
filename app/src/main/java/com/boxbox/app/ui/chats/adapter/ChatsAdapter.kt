package com.boxbox.app.ui.chats.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.boxbox.app.R
import com.boxbox.app.domain.model.ChatWithUser

class ChatsAdapter(
    private var list: List<ChatWithUser> = emptyList(),
    private var onItemSelected: (Int) -> Unit
) : RecyclerView.Adapter<ChatsViewHolder>() {

    fun updateList(newList:List<ChatWithUser>) {
        val chatsDiff= ChatsDiffUtil(list, newList)
        val result = DiffUtil.calculateDiff(chatsDiff)
        list = newList
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatsViewHolder {
        return ChatsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ChatsViewHolder,
        position: Int
    ) {
        holder.render(list[position], onItemSelected)
    }

    override fun getItemCount(): Int = list.size
}