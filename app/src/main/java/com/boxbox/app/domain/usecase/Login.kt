package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.Repository
import javax.inject.Inject

class Login @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(email, password)
    }
}