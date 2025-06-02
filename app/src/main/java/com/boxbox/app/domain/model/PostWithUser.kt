package com.boxbox.app.domain.model

data class PostWithUser(
    val post: Post,
    val user: User?
)