package com.boxbox.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val USER_ID_KEY = intPreferencesKey("user_id")
    }

    suspend fun saveUserId(userId: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    val userIdFlow: Flow<Int?> = context.dataStore.data
        .map { preferences ->
            preferences[USER_ID_KEY]
        }

    suspend fun getUserId(): Int? {
        return context.dataStore.data
            .map { it[USER_ID_KEY] }
            .firstOrNull()
    }

    suspend fun clearUserId() {
        context.dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
        }
    }
}