package com.boxbox.app.data.network.response

import com.google.gson.annotations.SerializedName

data class PostListResponse (
    @SerializedName("registros")
    val registros: Int,
    @SerializedName("posts")
    val posts: List<PostResponse>
)