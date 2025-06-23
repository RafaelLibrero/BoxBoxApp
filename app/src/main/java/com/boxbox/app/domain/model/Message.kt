package com.boxbox.app.domain.model

import java.util.Date

data class Message (
    val id: Int,
    val senderId: Int,
    val content: String,
    val sentAt: Date
)