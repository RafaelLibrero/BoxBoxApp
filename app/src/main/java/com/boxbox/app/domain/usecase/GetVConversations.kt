package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.VConversation
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetVConversations @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(position: Int, topicId: Int): Result<List<VConversation>> =
        repository.getVConversations(position, topicId)
}