package com.boxbox.app.domain.model

import com.boxbox.app.data.network.response.LoginRequest

data class Login(
    val email: String,
    val password: String
) {
    fun toData(): LoginRequest {
        return LoginRequest(
            email = email,
            password = password
        )
    }
}