package com.boxbox.app.data.repository

import android.util.Log
import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.repository.AuthRepository
import com.boxbox.app.domain.model.Login
import com.tuapp.data.storage.TokenStorage
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val apiService: ApiService,
    private val tokenStorage: TokenStorage
) : AuthRepository {

    override suspend fun login(login: Login): Result<String> {
        return runCatching {
            val data = login.toData()
            val response = apiService.login(data)
            response.token
        }
    }

    override fun saveToken(token: String) {
        tokenStorage.saveToken(token)
    }

    override fun getToken() = tokenStorage.getToken()

    override fun logout() {
        tokenStorage.clearToken()
    }

    override suspend fun register(username: String, email: String, password: String): Result<Unit> {
        return runCatching {
            val response = apiService.register(username, email, password)

            if (response.isSuccessful) {
                Unit
            } else {
                throw Exception(response.errorBody()?.string() ?: "Unknown error")
            }
        }
    }

    override suspend fun isTokenExpiringSoon(token: String): Boolean {
        val expiration = decodeJwtExpiration(token)
        val now = System.currentTimeMillis()
        return expiration == null || (expiration - now) < 5 * 60 * 1000
    }

    override suspend fun refreshToken(): Result<String> {
        val token = tokenStorage.getToken() ?: return Result.failure(Exception("Token no encontrado"))
        return runCatching {
            val response = apiService.refreshToken("Bearer $token")
            val newToken = response.token
            tokenStorage.saveToken(newToken)
            newToken
        }.onFailure {
            Log.e("AuthRepository", "Error refrescando token", it)
        }
    }

    private fun decodeJwtExpiration(token: String): Long? {
        try {
            val parts = token.split(".")
            if (parts.size < 2) return null
            val payload = parts[1]
            val decodedBytes = android.util.Base64.decode(payload, android.util.Base64.URL_SAFE or android.util.Base64.NO_PADDING or android.util.Base64.NO_WRAP)
            val json = String(decodedBytes)
            val obj = org.json.JSONObject(json)
            val exp = obj.optLong("exp", 0)
            if (exp == 0L) return null
            return exp * 1000 // convertir a millis
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}