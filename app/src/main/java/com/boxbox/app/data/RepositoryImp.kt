package com.boxbox.app.data

import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.Repository
import com.boxbox.app.domain.model.VTopic
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val apiService: ApiService): Repository {
    override suspend fun getVTopics(): List<VTopic>? {
        runCatching { apiService.getVTopics().map { it.toDomain() } }
            .onSuccess { return it }

        return null
    }
}