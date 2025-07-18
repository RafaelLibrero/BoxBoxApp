package com.boxbox.app.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.Register
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: Register) : ViewModel() {

    private var _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state: StateFlow<RegisterState> = _state

    private val _formData = MutableStateFlow(RegisterFormData())
    val formData: StateFlow<RegisterFormData> = _formData

    fun setStepOneData(username: String, email: String, password: String) {
        _formData.value = _formData.value.copy(
            username = username,
            email = email,
            password = password
        )
    }

    fun setStepTwoData(fullName: String) {
        _formData.value = _formData.value.copy(
            fullName = fullName
        )
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading
            registerUseCase(username, email, password)
                .onSuccess {
                    _state.value = RegisterState.Success
                }
                .onFailure { exception ->
                    _state.value = RegisterState.Error(exception.message ?: "Error desconocido")
                }
        }
    }
}

data class RegisterFormData(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val fullName: String = "",
)