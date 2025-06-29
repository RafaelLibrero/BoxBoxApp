package com.boxbox.app.data.repository

import android.util.Log
import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.model.Chat
import com.boxbox.app.domain.model.ChatSummary
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

    override suspend fun getVTopics(): Result<List<VTopic>> {
        return runCatching {
            val response = apiService.getVTopics()
            response.map { it.toDomain() }.toList()
        }
    }

    override suspend fun getTopic(id: Int): Result<Topic> {
        return runCatching {
            apiService.getTopic(id).toDomain()
        }
    }

    override suspend fun getVConversations(position: Int, topicId: Int): Result<List<VConversation>> {
        return runCatching {
            val response = apiService.getVConversations(position, topicId)
            response.conversations.map { it.toDomain() }.toMutableList()
        }
    }

    override suspend fun getPosts(position: Int, conversationId: Int): Result<List<Post>> {
        return runCatching {
            val response = apiService.getPosts(position, conversationId)
            response.posts.map { it.toDomain() }.toMutableList()
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

    override suspend fun getTeams(): Result<List<Team>> {
        return runCatching {
            val response = apiService.getTeams()
            response.map { it.toDomain() }.toMutableList()
        }
    }

    override suspend fun getTeam(id: Int): Result<Team> {
        return runCatching {
            apiService.getTeam(id).toDomain()
        }
    }

    override suspend fun getDrivers(): Result<List<Driver>> {
        return runCatching {
            val response = apiService.getDrivers()
            response.map { it.toDomain() }.toMutableList()
        }
    }

    override suspend fun getDriver(id: Int): Result<Driver> {
        return runCatching {
            apiService.getDriver(id).toDomain()
        }
    }

    override suspend fun getRaces(): Result<List<Race>> {
        return runCatching {
            val response = apiService.getRaces()
            response.map { it.toDomain() }.toMutableList()
        }
    }

    override suspend fun getUser(id: Int): Result<User> {
        val ghostUser = User(
            userId = -1,
            userName = "Usuario eliminado",
            email = null,
            registrationDate = null,
            lastAccess = null,
            biography = "Este usuario ya no está disponible",
            profilePicture = "",
            totalPosts = 0,
            teamId = null,
            driverId = null
        )

        return runCatching {
            apiService.getUser(id).toDomain()
        }.fold(
            onSuccess = { user ->
                Result.success(user)
            },
            onFailure = { error ->
                // Aquí decides si devuelves ghostUser o fallas la llamada según el tipo de error
                // Por ejemplo, si error es 404 o User not found => ghostUser
                // Si es otro error, podrías hacer return Result.failure(error)
                // Para simplificar, devolvemos ghostUser siempre:
                Result.success(ghostUser)
            }
        )
    }

    override suspend fun getProfile(): Result<User> {
        val token = tokenStorage.getToken() ?: return Result.failure(Exception("Token no encontrado"))
        return runCatching {
            apiService.getProfile("Bearer $token").toDomain()
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }
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

    override suspend fun getChat(id: Int, userId: Int): Result<Chat> {
        val token = tokenStorage.getToken() ?: return Result.failure(Exception("Token no encontrado"))
        return runCatching {
            apiService.getChat(id, "Bearer $token").toDomain(userId)
        }.onFailure {
            Log.e("API Error", "Error en la llamada a la API", it)
        }
    }

    override suspend fun getUserChats(userId: Int): Result<List<ChatSummary>> {
        val token = tokenStorage.getToken() ?: return Result.failure(Exception("Token no encontrado"))
        return runCatching {
            val response = apiService.getUserChats("Bearer $token")
            response.map {it.toDomain(userId) }.toMutableList()
        }
    }
}