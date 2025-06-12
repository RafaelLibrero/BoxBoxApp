package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetTeam @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int): Team? = repository.getTeam(id)
}