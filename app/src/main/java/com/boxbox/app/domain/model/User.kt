package com.boxbox.app.domain.model

import com.boxbox.app.data.network.request.UserRequest
import java.util.Date

data class User (
    val userId: Int,
    val userName: String,
    val email: String?,
    val registrationDate: Date,
    val lastAccess: Date,
    val biography: String?,
    val profilePicture: String,
    val totalPosts: Int,
    val teamId: Int?,
    val driverId: Int?
) {
    fun toData(): UserRequest {
        return UserRequest(
            userId = userId,
            userName = userName,
            email = email,
            biography = biography,
            profilePicture = profilePicture,
            teamId = teamId,
            driverId = driverId
        )
    }
}