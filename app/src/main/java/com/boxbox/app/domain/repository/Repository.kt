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
    suspend fun getVTopics(): List<VTopic>?
    suspend fun getTopic(id: Int): Topic?
    suspend fun getVConversations(position: Int, topicId: Int): List<VConversation>?
    suspend fun getPosts(position: Int, conversationId: Int): List<Post>?
    suspend fun createPost(post: Post): Result<Unit>
    suspend fun getTeams(): List<Team>?
    suspend fun getDrivers(): List<Driver>?
    suspend fun getRaces(): List<Race>?
    suspend fun getUser(id: Int): User?
    suspend fun getProfile(): User?
}