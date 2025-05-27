package com.boxbox.app.ui.auth

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
}
