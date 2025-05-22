package com.boxbox.app.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetVConversations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val getVConversationsUseCase: GetVConversations
): ViewModel() {
    private var _state = MutableStateFlow<ConversationsState>(ConversationsState.Loading)
    val state: StateFlow<ConversationsState> = _state

    fun getVConversations(position: Int, topicId: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getVConversationsUseCase(position, topicId)
            }
            if (result != null) {
                _state.value = ConversationsState.Success(result)
            } else {
                _state.value = ConversationsState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}