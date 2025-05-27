package com.boxbox.app.data.network

import com.boxbox.app.data.network.response.DriverResponse
import com.boxbox.app.data.network.response.LoginRequest
import com.boxbox.app.data.network.response.LoginResponse
import com.boxbox.app.data.network.response.PostListResponse
import com.boxbox.app.data.network.response.RaceResponse
import com.boxbox.app.data.network.response.TeamResponse
import com.boxbox.app.data.network.response.TopicResponse
import com.boxbox.app.data.network.response.UserResponse
import com.boxbox.app.data.network.response.VConversationListResponse
import com.boxbox.app.data.network.response.VTopicResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/topics")
    suspend fun getVTopics(): List<VTopicResponse>

    @GET("api/topics/{id}")
    suspend fun getTopic(@Path("id") id: Int): TopicResponse

    @GET("api/conversations/get/{position}/{topicId}")
    suspend fun getVConversations(
        @Path("position") position: Int,
        @Path("topicId") topicId: Int
    ): VConversationListResponse

    @GET("api/posts/get/{position}/{conversationId}")
    suspend fun getPosts(
        @Path("position") position: Int,
        @Path("conversationId") conversationId: Int
    ): PostListResponse

    @POST("api/auth/login")
    suspend fun login(@Body login: LoginRequest): LoginResponse

    @GET("api/teams")
    suspend fun getTeams(): List<TeamResponse>

    @GET("api/drivers")
    suspend fun getDrivers(): List<DriverResponse>

    @GET("api/races")
    suspend fun getRaces(): List<RaceResponse>

    @GET("api/users/profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserResponse

    @POST("api/users")
    suspend fun register(
        @Query("username") username: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Response<Unit>
}