package com.boxbox.app.domain.usecase

import androidx.paging.PagingData
import com.boxbox.app.domain.model.VConversation
import com.boxbox.app.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVConversations @Inject constructor(private val repository: Repository) {
    operator fun invoke(topicId: Int): Flow<PagingData<VConversation>> =
        repository.getPagedConversations(topicId)
}