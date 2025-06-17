package com.boxbox.app.domain.repository

import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Post
import com.boxbox.app.domain.model.Race
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.model.Topic
import com.boxbox.app.domain.model.User
import com.boxbox.app.domain.model.VConversation
import com.boxbox.app.domain.model.VTopic

interface Repository {
    suspend fun getVTopics(): Result<List<VTopic>>
    suspend fun getTopic(id: Int): Result<Topic>
    suspend fun getVConversations(position: Int, topicId: Int): Result<List<VConversation>>
    suspend fun getPosts(position: Int, conversationId: Int): Result<List<Post>>
    suspend fun createPost(post: Post): Result<Unit>
    suspend fun getTeams(): Result<List<Team>>
    suspend fun getTeam(id: Int): Result<Team>
    suspend fun getDrivers(): Result<List<Driver>>
    suspend fun getDriver(id: Int): Result<Driver>
    suspend fun getRaces(): Result<List<Race>>
    suspend fun getUser(id: Int): Result<User>
    suspend fun getProfile(): Result<User>
    suspend fun putUser(user: User): Result<Unit>
}