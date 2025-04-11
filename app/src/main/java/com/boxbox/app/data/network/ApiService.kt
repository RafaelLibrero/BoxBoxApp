package com.boxbox.app.data.network

import com.boxbox.app.data.network.response.TopicResponse
import com.boxbox.app.data.network.response.VTopicResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/api/topics")
    suspend fun getVTopics(): List<VTopicResponse>

    @GET("/api/topics/{id}")
    suspend fun getTopic(@Path("id") id: Int): TopicResponse
}