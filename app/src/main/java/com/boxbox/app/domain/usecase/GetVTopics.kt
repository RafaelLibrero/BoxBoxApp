package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.repository.Repository
import com.boxbox.app.domain.model.VTopic
import javax.inject.Inject

class GetVTopics @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): List<VTopic>? = repository.getVTopics()
}