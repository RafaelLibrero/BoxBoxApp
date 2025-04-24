package com.boxbox.app.ui.auth.login

sealed class LoginState {
    object Idle: LoginState()
    object Loading : LoginState()
    data class Success(val token: String) : LoginState()
    data class Error(val message: String) : LoginState()
}