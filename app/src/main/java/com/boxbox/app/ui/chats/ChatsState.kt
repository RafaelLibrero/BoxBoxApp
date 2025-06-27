package com.boxbox.app.ui.chats

import com.boxbox.app.domain.model.ChatWithUser

sealed class ChatsState {
    object Loading : ChatsState()
    data class Success(val chatsWithUsers: List<ChatWithUser>) : ChatsState()
    data class Error(val message: String) : ChatsState()
}