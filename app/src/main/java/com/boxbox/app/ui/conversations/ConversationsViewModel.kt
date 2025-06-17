package com.boxbox.app.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetTopic
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
    private val getVConversationsUseCase: GetVConversations,
    private val getTopicUseCase: GetTopic
) : ViewModel() {
    private var _state = MutableStateFlow<ConversationsState>(ConversationsState.Loading)
    val state: StateFlow<ConversationsState> = _state

    fun loadData(position: Int, topicId: Int) {
        viewModelScope.launch {
            val topicResult = withContext(Dispatchers.IO) { getTopicUseCase(topicId) }
            val conversationsResult = withContext(Dispatchers.IO) {
                getVConversationsUseCase(position, topicId)
            }

            topicResult.fold(
                onSuccess = { topic ->
                    conversationsResult.fold(
                        onSuccess = { conversations ->
                            _state.value = ConversationsState.Success(topic, conversations)
                        },
                        onFailure = {
                            _state.value = ConversationsState.Error("Error cargando conversaciones")
                        }
                    )
                },
                onFailure = {
                    _state.value = ConversationsState.Error("Error cargando tema")
                }
            )
        }
    }

}