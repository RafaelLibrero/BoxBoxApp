package com.boxbox.app.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPosts
) : ViewModel() {
    private var _state = MutableStateFlow<PostsState>(PostsState.Loading)
    val state: StateFlow<PostsState> = _state

    fun getPosts(position: Int, conversationId: Int) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                getPostsUseCase(position, conversationId)
            }
            if (result != null) {
                _state.value = PostsState.Success(result)
            } else
                _state.value = PostsState.Error("Ha ocurrido un error, intentelo mas tarde")
        }
    }
}
