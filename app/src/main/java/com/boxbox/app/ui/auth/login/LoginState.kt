package com.boxbox.app.ui.auth.login

import com.boxbox.app.domain.model.User

sealed class LoginState {
    object Idle: LoginState()
    object Loading : LoginState()
    data class Success(val profile: User) : LoginState()
    data class TokenObtained(val token: String): LoginState()
    data class Error(val message: String) : LoginState()
}