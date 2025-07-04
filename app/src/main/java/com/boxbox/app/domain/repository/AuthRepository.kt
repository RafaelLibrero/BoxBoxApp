package com.boxbox.app.domain.repository

import com.boxbox.app.domain.model.Login

interface AuthRepository {
    suspend fun login(login: Login): Result<String>
    fun saveToken(token: String)
    fun getToken(): String?
    fun logout()
    suspend fun register(username: String, email: String, password: String): Result<Unit>
    suspend fun isTokenExpiringSoon(token: String): Boolean
    suspend fun refreshToken(): Result<String>
}