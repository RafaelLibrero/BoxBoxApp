package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Topic
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetTopic @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int): Result<Topic> = repository.getTopic(id)
}