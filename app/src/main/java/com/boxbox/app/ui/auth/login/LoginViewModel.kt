package com.boxbox.app.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: Login) : ViewModel() {

    private var _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            val result = loginUseCase(email, password)
            result.fold(
                onSuccess = { token ->
                    _state.value = LoginState.Success(token)
                },
                onFailure = { exception ->
                    _state.value = LoginState.Error(exception.message ?: "Error desconocido")
                }
            )
        }
    }
}