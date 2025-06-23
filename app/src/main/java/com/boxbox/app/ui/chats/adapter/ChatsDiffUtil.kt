package com.boxbox.app.ui.chats.adapter

import androidx.recyclerview.widget.DiffUtil
import com.boxbox.app.domain.model.Chat

class ChatsDiffUtil(
    private val oldList: List<Chat>,
    private val newList: List<Chat>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}