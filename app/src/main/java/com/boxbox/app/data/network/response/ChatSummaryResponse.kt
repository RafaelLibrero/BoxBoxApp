package com.boxbox.app.data.network.response

import com.boxbox.app.domain.model.ChatSummary
import com.google.gson.annotations.SerializedName

data class ChatSummaryResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("user1Id") val user1Id: Int,
    @SerializedName("user2Id") val user2Id: Int,
    @SerializedName("lastMessage") val lastMessage: MessageResponse?
) {
    fun toDomain(currentUserId: Int): ChatSummary {
        val otherUserId = if (user1Id == currentUserId) user2Id else user1Id
        return ChatSummary(
            id = id,
            otherUserId = otherUserId,
            lastMessage = lastMessage?.toDomain()
        )
    }
}