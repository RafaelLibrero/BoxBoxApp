package com.boxbox.app.domain.repository

import com.boxbox.app.domain.model.VTopic

interface Repository {
    suspend fun getVTopics(): List<VTopic>?
}