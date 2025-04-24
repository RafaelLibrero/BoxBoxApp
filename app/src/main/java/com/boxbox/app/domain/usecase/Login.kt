package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.repository.AuthRepository
import com.boxbox.app.domain.model.Login
import javax.inject.Inject

class Login @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<String> {
        return authRepository.login(Login(email, password))
    }
}