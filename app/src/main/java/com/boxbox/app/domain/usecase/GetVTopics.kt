package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.Repository
import javax.inject.Inject

class GetVTopics @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getVTopics()
}