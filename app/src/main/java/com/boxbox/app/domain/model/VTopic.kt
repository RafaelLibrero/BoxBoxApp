package com.boxbox.app.domain.model

data class VTopic (
    val topicId: Int,
    val title: String,
    val description: String,
    val conversations: Int,
    val posts: Int,
    val lastMessage: Int
)