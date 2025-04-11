package com.boxbox.app.domain

import com.boxbox.app.domain.model.VTopic

interface Repository {
    suspend fun getVTopics(): MutableList<VTopic>?
}