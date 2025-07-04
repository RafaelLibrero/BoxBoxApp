package com.boxbox.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.data.local.DataStoreManager
import com.boxbox.app.domain.repository.AuthRepository
import com.boxbox.app.domain.usecase.GetProfile
import com.tuapp.data.storage.TokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val dataStoreManager: DataStoreManager,
    private val getProfileUseCase: GetProfile,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    init {
        checkAuth()
    }

    fun checkAuth() {
        viewModelScope.launch {
            val token = tokenStorage.getToken()
            val userId = dataStoreManager.getUserId()
            if (!token.isNullOrEmpty() && userId != null) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun saveToken(token: String) {
        tokenStorage.saveToken(token)
        checkIfAuthenticated()
    }

    suspend fun saveUserId(id: Int) {
        dataStoreManager.saveUserId(id)
        checkIfAuthenticated()
    }

    suspend fun logout() {
        tokenStorage.clearToken()
        dataStoreManager.clearUserId()
        _authState.value = AuthState.Unauthenticated
    }

    fun getToken(): String? = tokenStorage.getToken()
    suspend fun getUserId(): Int? = dataStoreManager.getUserId()

    private fun checkIfAuthenticated() {
        viewModelScope.launch {
            val token = getToken()
            val userId = getUserId()
            if (!token.isNullOrEmpty() && userId != null) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    suspend fun fetchUserImageUrlFromApi(): String? {
        val result = getProfileUseCase()
        return result.fold(
            onSuccess = { user -> user.profilePicture },
            onFailure = { null }
        )
    }

    suspend fun isTokenExpiringSoon(token: String): Boolean {
        return authRepository.isTokenExpiringSoon(token)
    }

    suspend fun refreshToken(): Boolean {
        val result = authRepository.refreshToken()
        return result.isSuccess
    }


}