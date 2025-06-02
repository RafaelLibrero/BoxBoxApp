package com.boxbox.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.data.local.DataStoreManager
import com.tuapp.data.storage.TokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState
    private val _profilePictureUrlState = MutableStateFlow<String?>(null)
    val profilePictureUrlState: StateFlow<String?> = _profilePictureUrlState

    init {
        viewModelScope.launch {
            dataStoreManager.profilePictureUrlFlow.collect { url ->
                _profilePictureUrlState.value = url
            }
        }
    }

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

    suspend fun saveUserId(id: Int){
        dataStoreManager.saveUserId(id)
        checkIfAuthenticated()
    }

    suspend fun saveUserPicture(picture: String){
        dataStoreManager.saveProfilePictureUrl(picture)
    }

    suspend fun logout() {
        tokenStorage.clearToken()
        dataStoreManager.clearUserId()
        _authState.value = AuthState.Unauthenticated
    }

    fun getToken(): String? = tokenStorage.getToken()
    suspend fun getUserId(): Int? = dataStoreManager.getUserId()
    suspend fun getProfilePicture(): String? = dataStoreManager.getProfilePictureUrl()

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


}