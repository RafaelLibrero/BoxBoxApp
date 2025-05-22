package com.boxbox.app.ui.posts

import com.boxbox.app.domain.model.Post

sealed class PostsState {
    object Loading : PostsState()
    data class Success(val posts: List<Post>) : PostsState()
    data class Error(val message: String) : PostsState()
}