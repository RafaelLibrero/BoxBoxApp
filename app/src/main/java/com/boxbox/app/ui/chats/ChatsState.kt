package com.boxbox.app.ui.chats

import com.boxbox.app.domain.model.Chat

sealed class ChatsState {
    object Loading : ChatsState()
    data class Success(val posts: List<Chat>) : ChatsState()
    data class Error(val message: String) : ChatsState()
}