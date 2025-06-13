package com.boxbox.app.data.repository

import android.util.Log
import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Post
import com.boxbox.app.domain.model.Race
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.model.Topic
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
        return runCatching {
            val response = apiService.getVTopics()
            response.map { it.toDomain() }.toMutableList()
        }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
    }

    override suspend fun getTopic(id: Int): Topic? {
        return runCatching {
            apiService.getTopic(id).toDomain()
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }.getOrThrow()
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
        return runCatching {
            val response = apiService.getTeams()
            response.map { it.toDomain() }.toMutableList()
        }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }

    }

    override suspend fun getTeam(id: Int): Team? {
        return runCatching {
            apiService.getTeam(id).toDomain()
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }.getOrThrow()
    }

    override suspend fun getDrivers(): List<Driver>? {
        return runCatching {
            val response = apiService.getDrivers()
            response.map { it.toDomain() }.toMutableList()
        }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
    }

    override suspend fun getDriver(id: Int): Driver? {
        return runCatching {
            apiService.getDriver(id).toDomain()
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }.getOrThrow()
    }

    override suspend fun getRaces(): List<Race>? {
        return runCatching {
            val response = apiService.getRaces()
            response.map { it.toDomain() }.toMutableList()
        }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
    }

    override suspend fun getUser(id: Int): User? {
        return runCatching {
            apiService.getUser(id).toDomain()
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }.getOrThrow()
    }

    override suspend fun getProfile(): User? {
        val token = tokenStorage.getToken() ?: throw Exception("Token no encontrado")
        return runCatching {
            apiService.getProfile("Bearer $token").toDomain()
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }.getOrThrow()
    }

    override suspend fun putUser(user: User): Result<Unit> {
        val token = tokenStorage.getToken() ?: throw Exception("Token no encontrado")
        return runCatching {
            val response = apiService.editUser(user.toData(), "Bearer $token")
            if (response.isSuccessful) {
                Unit
            } else {
                throw Exception(response.errorBody()?.string() ?: "Unknown error")
            }
        }
    }
}