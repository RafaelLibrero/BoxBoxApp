package com.boxbox.app.domain.repository

import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Race
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.model.VTopic

interface Repository {
    suspend fun getVTopics(): List<VTopic>?
    suspend fun getTeams(): List<Team>?
    suspend fun getDrivers(): List<Driver>?
    suspend fun getRaces(): List<Race>?
}