package com.boxbox.app.ui.conversations

import com.boxbox.app.domain.model.Topic
import com.boxbox.app.domain.model.VConversation

sealed class ConversationsState {
    object Loading : ConversationsState()
    data class Success(
        val topic: Topic,
        val conversations: List<VConversation>
    ) : ConversationsState()

    data class Error(val message: String) : ConversationsState()
}