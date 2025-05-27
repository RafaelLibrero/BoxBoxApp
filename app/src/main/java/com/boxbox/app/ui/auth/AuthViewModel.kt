package com.boxbox.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuapp.data.storage.TokenStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val tokenStorage: TokenStorage
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    val authState: StateFlow<AuthState> = _authState

    init {
        checkToken()
    }

    fun checkToken() {
        viewModelScope.launch {
            val token = tokenStorage.getToken()
            if (!token.isNullOrEmpty()) {
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    fun saveToken(token: String) {
        tokenStorage.saveToken(token)
        _authState.value = AuthState.Authenticated
    }

    fun clearToken() {
        tokenStorage.clearToken()
        _authState.value = AuthState.Unauthenticated
    }

    fun getToken(): String? {
        return tokenStorage.getToken()
    }
}