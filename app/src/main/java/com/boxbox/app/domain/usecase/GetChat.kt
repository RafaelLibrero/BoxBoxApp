package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Chat
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetChat @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int): Result<Chat> = repository.getChat(id)
}