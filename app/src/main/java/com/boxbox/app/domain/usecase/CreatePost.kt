package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Post
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class CreatePost @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(post: Post): Result<Unit> {
        return repository.createPost(post)
    }
}