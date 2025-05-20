package com.boxbox.app.data.repository

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
}