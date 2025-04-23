package com.boxbox.app.data

import android.util.Log
import com.boxbox.app.data.network.ApiService
import com.boxbox.app.data.network.response.LoginRequest
import com.boxbox.app.domain.Repository
import com.boxbox.app.domain.model.Login
import com.boxbox.app.domain.model.VTopic
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val apiService: ApiService): Repository {

    override suspend fun getVTopics(): List<VTopic>? {
        runCatching { apiService.getVTopics().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure {
                // Hacer log si hay error
                Log.e("API Error", "Error en la llamada a la API", it)
            }
            .getOrElse {
                // Log de si hubo un error y se retornó una lista vacía
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun login(login: Login): Result<String> {
        return runCatching {
            val data = login.toData()
            val response = apiService.login(data)
            response.token
        }
    }
}