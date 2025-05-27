package com.boxbox.app.data.network

import com.boxbox.app.data.network.response.DriverResponse
import com.boxbox.app.data.network.response.LoginRequest
import com.boxbox.app.data.network.response.LoginResponse
import com.boxbox.app.data.network.response.PostResponse
import com.boxbox.app.data.network.response.RaceResponse
import com.boxbox.app.data.network.response.TeamResponse
import com.boxbox.app.data.network.response.TopicResponse
import com.boxbox.app.data.network.response.UserResponse
import com.boxbox.app.data.network.response.VConversationListResponse
import com.boxbox.app.data.network.response.VTopicResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("api/posts/get/{position}/{conversationId")
    suspend fun getPosts(
        @Path("position") position: Int,
        @Path("conversationId") conversationId: Int
    ): List<PostResponse>

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

    @FormUrlEncoded
    @POST("api/users")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<Unit>
}