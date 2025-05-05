package com.boxbox.app.data.repository

import android.util.Log
import com.boxbox.app.data.network.ApiService
import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Race
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.repository.Repository
import com.boxbox.app.domain.model.VTopic
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val apiService: ApiService) : Repository {

    override suspend fun getVTopics(): List<VTopic>? {
        runCatching { apiService.getVTopics().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getTeams(): List<Team>? {
        runCatching { apiService.getTeams().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getDrivers(): List<Driver>? {
        runCatching { apiService.getDrivers().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }

    override suspend fun getRaces(): List<Race>? {
        runCatching { apiService.getRaces().map { it.toDomain() }.toMutableList() }
            .onSuccess { return it }
            .onFailure { Log.e("API Error", "Error en la llamada a la API", it) }
            .getOrElse {
                Log.e("API Error", "Error al mapear la respuesta, retornando lista vacía")
                emptyList()
            }
        return null
    }
}