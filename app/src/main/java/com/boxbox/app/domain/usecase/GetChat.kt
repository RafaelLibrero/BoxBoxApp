package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.ChatSummary
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetChat @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int, userId: Int): Result<ChatSummary> = repository.getChat(id, userId)
}