package com.boxbox.app.domain.model

data class ChatSummary(
    val id: Int,
    val otherUserId: Int,
    val lastMessage: Message?
)