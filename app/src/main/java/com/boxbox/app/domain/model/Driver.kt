package com.boxbox.app.domain.model

data class Driver(
    val driverID: Int,
    val driverName: String,
    val carNumber: Int,
    val teamID: Int,
    val flag: String,
    val imagen: String,
    val points: Int
)