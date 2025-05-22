package com.boxbox.app.domain.model

import java.util.Date

data class Post(
    val postId: Int,
    val conversationId: Int,
    val userId: Int,
    val text: String,
    val createdAt: Date,
    val estado: Int
)