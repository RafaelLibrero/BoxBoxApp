package com.boxbox.app.data.network.response

import com.boxbox.app.data.network.DateJsonAdapter
import com.boxbox.app.domain.model.VConversation
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.Date

data class VConversationResponse (
    @SerializedName("conversationId") val conversationId: Int,
    @SerializedName("topicId") val topicId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("entryCount") val entryCount: Int,
    @JsonAdapter(DateJsonAdapter::class) @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("postCount") val postCount: Int,
    @SerializedName("lastMessage") val lastMessage: Int
) {
    fun toDomain(): VConversation {
        return VConversation(
            conversationId = conversationId,
            topicId = topicId,
            userId = userId,
            title = title,
            entryCount = entryCount,
            createdAt = createdAt,
            postCount = postCount,
            lastMessage = lastMessage
        )
    }
}