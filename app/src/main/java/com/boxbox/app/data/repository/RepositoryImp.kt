package com.boxbox.app.data.repository

import android.util.Log
import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Post
import com.boxbox.app.domain.model.Race
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.model.User
import com.boxbox.app.domain.model.VConversation
import com.boxbox.app.domain.repository.Repository
import com.boxbox.app.domain.model.VTopic
import com.tuapp.data.storage.TokenStorage
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: ApiService, private val tokenStorage: TokenStorage
) : Repository {

    override suspend fun getVTopics(): List<VTopic>? {
        runCatching { apiService.getVTopics().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getVConversations(position: Int, topicId: Int): List<VConversation>? {
        return runCatching {
            val response = apiService.getVConversations(position, topicId)
            response.conversations.map { it.toDomain() }.toMutableList()
        }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getPosts(position: Int, conversationId: Int): List<Post>? {
        return runCatching {
            val response = apiService.getPosts(position, conversationId)
            response.posts.map { it.toDomain() }.toMutableList()
        }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun createPost(post: Post): Result<Unit> {
        val token = tokenStorage.getToken() ?: throw Exception("Token no encontrado")
        return runCatching {
            val response = apiService.createPost(post.toData(), "Bearer $token")
            if (response.isSuccessful) {
                Unit
            } else {
                throw Exception(response.errorBody()?.string() ?: "Unknown error")
            }
        }
    }

    override suspend fun getTeams(): List<Team>? {
        runCatching { apiService.getTeams().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getDrivers(): List<Driver>? {
        runCatching { apiService.getDrivers().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getRaces(): List<Race>? {
        runCatching { apiService.getRaces().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getProfile(): User? {
        val token = tokenStorage.getToken() ?: throw Exception("Token no encontrado")
        return runCatching {
            apiService.getProfile("Bearer $token").toDomain()
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }.getOrThrow()
    }
}