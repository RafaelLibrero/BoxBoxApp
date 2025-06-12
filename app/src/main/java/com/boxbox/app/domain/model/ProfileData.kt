package com.boxbox.app.domain.model

data class ProfileData(
    val user: User,
    val team: Team?,
    val driver: Driver?
)