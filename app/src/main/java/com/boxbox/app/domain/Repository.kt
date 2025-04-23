package com.boxbox.app.domain

import com.boxbox.app.domain.model.Login
import com.boxbox.app.domain.model.VTopic

interface Repository {
    suspend fun getVTopics(): List<VTopic>?

    suspend fun login(login: Login): Result<String>
}