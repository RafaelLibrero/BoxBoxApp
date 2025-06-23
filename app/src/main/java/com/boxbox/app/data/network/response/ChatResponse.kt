package com.boxbox.app.data.network.response

import com.boxbox.app.domain.model.Chat
import com.google.gson.annotations.SerializedName

data class ChatResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("user1Id") val user1Id: Int,
    @SerializedName("user2Id") val user2Id: Int,
    @SerializedName("messages") val messages: List<MessageResponse>
) {
    fun toDomain(): Chat {
        return Chat(
            id = id,
            user1Id = user1Id,
            user2Id = user2Id,
            messages = messages.map {it.toDomain()}
        )
    }
}