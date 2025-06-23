package com.boxbox.app.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.data.local.DataStoreManager
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
    private val dataStoreManager: DataStoreManager
): ViewModel() {
    private var _state = MutableStateFlow<ChatsState>(ChatsState.Loading)
    val state: StateFlow<ChatsState> = _state

    fun getUserChats() {
        viewModelScope.launch {
            _state.value = ChatsState.Loading
            val userId = dataStoreManager.userIdFlow.firstOrNull()
            if (userId == null) {
                _state.value = ChatsState.Error("No se encontró usuario")
                return@launch
            }
            val result = withContext(Dispatchers.IO) {
                getUserChatsUseCase(userId)
            }
            result.fold(
                onSuccess = { chats ->
                    _state.value = ChatsState.Success(chats)
                },
                onFailure = { error ->
                    _state.value = ChatsState.Error("Ha ocurrido un error: ${error.message}")
                }
            )
        }
    }
}