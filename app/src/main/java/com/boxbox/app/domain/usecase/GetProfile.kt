package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.model.User
import com.boxbox.app.domain.repository.Repository
import javax.inject.Inject

class GetProfile @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): User? = repository.getProfile()
}