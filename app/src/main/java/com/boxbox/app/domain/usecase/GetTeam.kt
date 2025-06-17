package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetTeam @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int): Result<Team> = repository.getTeam(id)
}