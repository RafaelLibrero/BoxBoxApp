package com.boxbox.app.domain.model

import java.util.Date

data class User (
    val userId: Int,
    val userName: String,
    val email: String,
    val registrationDate: Date,
    val lastAccess: Date,

    val profilePicture: String,
    val totalPosts: Int,
    val teamId: Int,
    val driverId: Int
)