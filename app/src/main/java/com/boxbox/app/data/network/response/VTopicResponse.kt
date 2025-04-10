package com.boxbox.app.data.network.response

import com.google.gson.annotations.SerializedName

data class VTopicResponse (
    @SerializedName("topicId") val topicId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("conversations") val conversations: Int,
    @SerializedName("posts") val posts: Int,
    @SerializedName("lastMessage") val lastMessage: Int
)