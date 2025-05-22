package com.boxbox.app.domain.model

import java.util.Date

data class VConversation (
    val conversationId: Int,
    val topicId: Int,
    val userId: Int,
    val title: String,
    val entryCount: Int,
    val createdAt: Date,
    val postCount: Int,
    val lastMessage: Int
)