package com.boxbox.app.domain.usecase

import com.boxbox.app.domain.repository.AuthRepository
import javax.inject.Inject

class Register @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(username: String, email: String, password: String) {
        return authRepository.register(username, email, password)
    }
}