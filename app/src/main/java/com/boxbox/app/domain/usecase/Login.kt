package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.Repository
import com.boxbox.app.domain.model.Login
import javax.inject.Inject

class Login @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return repository.login(Login(email, password))
    }
}