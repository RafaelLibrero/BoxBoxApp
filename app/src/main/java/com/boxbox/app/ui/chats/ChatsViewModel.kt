package com.boxbox.app.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.data.local.DataStoreManager
import com.boxbox.app.domain.model.ChatWithUser
import com.boxbox.app.domain.usecase.GetUser
import com.boxbox.app.domain.usecase.GetUserChats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel @Inject constructor(
    private val getUserChatsUseCase: GetUserChats,
    private val getUserUseCase: GetUser,
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private var _state = MutableStateFlow<ChatsState>(ChatsState.Loading)
    val state: StateFlow<ChatsState> = _state

    fun getUserChats() {
        viewModelScope.launch {
            _state.value = ChatsState.Loading
            val currentUserId = dataStoreManager.userIdFlow.firstOrNull()
            if (currentUserId == null) {
                _state.value = ChatsState.Error("No se encontró usuario")
                return@launch
            }

            val chatResult = withContext(Dispatchers.IO) {
                getUserChatsUseCase(currentUserId)
            }

            chatResult.fold(
                onSuccess = { chats ->
                    val chatsWithUsers = chats.mapNotNull { chat ->
                        val otherUserId = chat.otherUserId
                        val user = withContext(Dispatchers.IO) {
                            getUserUseCase(otherUserId).getOrNull()
                        }
                        if (user != null) ChatWithUser(chat, user) else null
                    }
                    _state.value = ChatsState.Success(chatsWithUsers)
                },
                onFailure = { error ->
                    _state.value = ChatsState.Error("Ha ocurrido un error: ${error.message}")
                }
            )
        }
    }

}