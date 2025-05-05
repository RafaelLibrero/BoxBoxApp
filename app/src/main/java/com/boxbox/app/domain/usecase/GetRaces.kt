package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Race
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetRaces @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): List<Race>? = repository.getRaces()
}