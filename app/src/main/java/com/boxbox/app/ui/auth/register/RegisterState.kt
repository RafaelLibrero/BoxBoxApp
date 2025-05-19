package com.boxbox.app.ui.auth.register

sealed class RegisterState {
    object Idle : RegisterState()
    object Loading : RegisterState()
    object Success: RegisterState()
    data class Error(val message: String) : RegisterState()
}