package com.boxbox.app.data.network

import com.boxbox.app.data.network.response.DriverResponse
import com.boxbox.app.data.network.response.LoginRequest
import com.boxbox.app.data.network.response.LoginResponse
import com.boxbox.app.data.network.response.RaceResponse
import com.boxbox.app.data.network.response.TeamResponse
import com.boxbox.app.data.network.response.TopicResponse
import com.boxbox.app.data.network.response.UserResponse
import com.boxbox.app.data.network.response.VTopicResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("api/topics")
    suspend fun getVTopics(): List<VTopicResponse>

    @GET("api/topics/{id}")
    suspend fun getTopic(@Path("id") id: Int): TopicResponse

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
        @Path("username") username: String,
        @Path("email") email: String,
        @Path("password") password: String
    )
}