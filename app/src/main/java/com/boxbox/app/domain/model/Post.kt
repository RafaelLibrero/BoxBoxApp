package com.boxbox.app.domain.model

import com.boxbox.app.data.network.request.PostRequest
import java.util.Date

data class Post(
    val postId: Int? = null,
    val conversationId: Int,
    val userId: Int,
    val text: String,
    val createdAt: Date? = null,
    val estado: Int? = null
) {
    fun toData(): PostRequest {
        return PostRequest(
            conversationId = conversationId,
            userId = userId,
            text = text,
        )
    }
}