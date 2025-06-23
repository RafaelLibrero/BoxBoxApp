package com.boxbox.app.data.network.response

import com.boxbox.app.data.network.DateJsonAdapter
import com.boxbox.app.domain.model.Message
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.Date

data class MessageResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("senderId") val senderId: Int,
    @SerializedName("content") val content: String,
    @JsonAdapter(DateJsonAdapter::class) @SerializedName("sentAt") val sentAt: Date
) {
    fun toDomain(): Message {
        return Message(
            id = id,
            senderId = senderId,
            content = content,
            sentAt = sentAt
        )
    }
}