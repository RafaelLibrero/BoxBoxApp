package com.boxbox.app.data.network.response

import com.boxbox.app.data.network.DateJsonAdapter
import com.boxbox.app.domain.model.Post
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.Date

data class PostResponse (
    @SerializedName("postId") val postId: Int,
    @SerializedName("conversationId") val conversationId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("text") val text: String,
    @JsonAdapter(DateJsonAdapter::class) @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("estado") val estado: Int
) {
    fun toDomain(): Post {
        return Post(
            postId = postId,
            conversationId = conversationId,
            userId = userId,
            text = text,
            createdAt = createdAt,
            estado = estado
        )
    }
}