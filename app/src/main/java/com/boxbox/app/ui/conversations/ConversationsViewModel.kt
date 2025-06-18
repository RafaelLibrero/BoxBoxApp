package com.boxbox.app.ui.conversations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.boxbox.app.domain.model.VConversation
import com.boxbox.app.domain.usecase.GetTopic
import com.boxbox.app.domain.usecase.GetVConversations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
    private val getPagedConversationsUseCase: GetVConversations
) : ViewModel() {
    private var _state = MutableStateFlow<ConversationsState>(ConversationsState.Loading)
    val state: StateFlow<ConversationsState> = _state

    fun getPagedConversations(topicId: Int): Flow<PagingData<VConversation>> {
        return getPagedConversationsUseCase(topicId)
            .cachedIn(viewModelScope)
    }
}