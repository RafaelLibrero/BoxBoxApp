package com.boxbox.app.domain.model

import java.util.Date

data class Race(
    val raceID: Int,
    val raceName: String,
    val image: String,
    val location: String,
    val startDate: Date,
    val endDate: Date,
    val winnerDriverId: Int
)