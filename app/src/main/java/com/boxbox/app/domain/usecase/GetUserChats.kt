package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.ChatSummary
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetUserChats @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(userId: Int): Result<List<ChatSummary>> = repository.getUserChats(userId)
}