package com.boxbox.app.domain.model

data class Chat (
    val id: Int,
    val otherUserId: Int,
    val messages: List<Message>
)