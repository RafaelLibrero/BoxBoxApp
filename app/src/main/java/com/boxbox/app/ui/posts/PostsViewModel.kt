package com.boxbox.app.ui.posts

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.data.local.DataStoreManager
import com.boxbox.app.domain.model.Post
import com.boxbox.app.domain.model.PostWithUser
import com.boxbox.app.domain.usecase.CreatePost
import com.boxbox.app.domain.usecase.GetPosts
import com.boxbox.app.domain.usecase.GetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val getPostsUseCase: GetPosts,
    private val createPostUseCase: CreatePost,
    private val getUserUseCase: GetUser,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private var _state = MutableStateFlow<PostsState>(PostsState.Loading)
    val state: StateFlow<PostsState> = _state

    private var pollingJob: Job? = null

    fun getPosts(position: Int, conversationId: Int) {
        viewModelScope.launch {
            val posts = withContext(Dispatchers.IO) {
                getPostsUseCase(position, conversationId)
            }

            if (posts == null) {
                _state.value = PostsState.Error("Error cargando posts")
                return@launch
            }

            val postWithUsers = posts.map { post ->
                val user = withContext(Dispatchers.IO) {
                    getUserUseCase(post.userId)
                }
                PostWithUser(post, user)
            }

            _state.value = PostsState.Success(postWithUsers)
        }
    }

    fun createPost(content: String, position: Int, conversationId: Int) {
        viewModelScope.launch {
            val userId = dataStoreManager.userIdFlow.firstOrNull()
            if (userId == null) {
                _state.value = PostsState.Error("No se encontró usuario")
                return@launch
            }

            val newPost = Post(
                conversationId = conversationId,
                userId = userId,
                text = content
            )

            val result = withContext(Dispatchers.IO) {
                createPostUseCase(newPost)
            }

            if (!result.isSuccess) {
                _state.value = PostsState.Error("Error al crear el post")
                Log.i("PostsViewModel", result.toString())
            }
        }
    }

    fun startAutoRefresh(position: Int, conversationId: Int, intervalMillis: Long = 5000L) {
        if (pollingJob?.isActive == true) return // Evitar lanzar más de un polling

        pollingJob = viewModelScope.launch {
            while (isActive) {
                getPosts(position, conversationId)
                delay(intervalMillis)
            }
        }
    }

    fun stopAutoRefresh() {
        pollingJob?.cancel()
        pollingJob = null
    }
}
