package com.boxbox.app.data.network.request

import com.google.gson.annotations.SerializedName

data class UserRequest (
    @SerializedName("userId") val userId: Int,
    @SerializedName("userName") val userName: String,
    @SerializedName("email") val email: String?,
    @SerializedName("biography") val biography: String?,
    @SerializedName("profilePicture") val profilePicture: String,
    @SerializedName("teamId") val teamId: Int?,
    @SerializedName("driverId") val driverId: Int?
)