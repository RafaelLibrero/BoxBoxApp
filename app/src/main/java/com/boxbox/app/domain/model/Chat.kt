package com.boxbox.app.domain.model

data class Chat(
    val id: Int,
    val user1Id: Int,
    val user2Id: Int,
    val messages: List<Message>
)