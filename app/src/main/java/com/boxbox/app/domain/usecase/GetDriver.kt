package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetDriver @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: Int): Driver? = repository.getDriver(id)
}