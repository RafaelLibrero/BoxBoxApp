package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Post
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetPosts @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(position: Int, conversationId: Int): Result<List<Post>> =
        repository.getPosts(position, conversationId)
}