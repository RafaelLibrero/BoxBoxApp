package com.boxbox.app.data.network.request

import com.google.gson.annotations.SerializedName

data class PostRequest (
    @SerializedName("conversationId") val conversationId: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("text") val text: String,
)