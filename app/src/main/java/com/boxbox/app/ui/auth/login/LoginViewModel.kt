package com.boxbox.app.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetProfile
import com.boxbox.app.domain.usecase.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: Login,
    private val getProfileUseCase: GetProfile,
) : ViewModel() {

    private var _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            loginUseCase(email, password).fold(
                onSuccess = { token ->
                    _state.value = LoginState.TokenObtained(token)
                },
                onFailure = { error ->
                    _state.value = LoginState.Error(error.message ?: "Error desconocido")
                }
            )
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = getProfileUseCase()
            result.fold(
                onSuccess = { profile ->
                    _state.value = LoginState.Success(profile)
                },
                onFailure = {
                    _state.value = LoginState.Error("No se pudo obtener perfil")
                }
            )
        }
    }
}