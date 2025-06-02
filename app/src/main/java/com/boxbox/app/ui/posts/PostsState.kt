package com.boxbox.app.ui.posts

import com.boxbox.app.domain.model.PostWithUser

sealed class PostsState {
    object Loading : PostsState()
    data class Success(val posts: List<PostWithUser>) : PostsState()
    data class Error(val message: String) : PostsState()
}