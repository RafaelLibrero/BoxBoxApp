package com.boxbox.app.ui.chats.adapter

import androidx.recyclerview.widget.DiffUtil
import com.boxbox.app.domain.model.ChatWithUser

class ChatsDiffUtil(
    private val oldList: List<ChatWithUser>,
    private val newList: List<ChatWithUser>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].chat.id == newList[newItemPosition].chat.id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}