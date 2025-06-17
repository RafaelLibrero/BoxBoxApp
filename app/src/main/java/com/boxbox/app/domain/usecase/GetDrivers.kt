package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetDrivers @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): Result<List<Driver>> = repository.getDrivers()
}