package com.boxbox.app.data.network.response

import com.boxbox.app.domain.model.Topic
import com.google.gson.annotations.SerializedName

data class TopicResponse(
    @SerializedName("topicId") val topicId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
) {
    fun toDomain(): Topic {
        return Topic(
            topicId = topicId,
            title = title,
            description = description
        )
    }
}