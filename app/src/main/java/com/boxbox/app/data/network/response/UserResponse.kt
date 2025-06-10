package com.boxbox.app.data.network.response

import com.boxbox.app.data.network.DateJsonAdapter
import com.boxbox.app.domain.model.User
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserResponse (
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("email") val email: String,
    @JsonAdapter(DateJsonAdapter::class) @SerializedName("registrationDate") val registrationDate: Date,
    @JsonAdapter(DateJsonAdapter::class) @SerializedName("lastAccess") val lastAccess: Date,
    @SerializedName("biography") val biography: String,
    @SerializedName("profilePicture") val profilePicture: String,
    @SerializedName("totalPosts") val totalPosts: Int,
    @SerializedName("teamId") val teamId: Int,
    @SerializedName("driverId") val driverId: Int
)
{
    fun toDomain(): User {
        return User(
            userId = userId,
            userName = userName,
            email = email,
            registrationDate = registrationDate,
            lastAccess = lastAccess,
            biography = biography,
            profilePicture = profilePicture,
            totalPosts = totalPosts,
            teamId = teamId,
            driverId = driverId
        )
    }
}
