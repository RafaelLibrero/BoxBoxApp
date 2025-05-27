package com.boxbox.app.data.network.response

import com.google.gson.annotations.SerializedName

data class VConversationListResponse (

    @SerializedName("registros")
    val registros: Int,
    @SerializedName("conversations")
    val conversations: List<VConversationResponse>
)