package com.boxbox.app.ui.profileEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.model.User
import com.boxbox.app.domain.usecase.EditUser
import com.boxbox.app.domain.usecase.GetDrivers
import com.boxbox.app.domain.usecase.GetProfile
import com.boxbox.app.domain.usecase.GetTeams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val editProfileUseCase: EditUser,
    private val getProfileUseCase: GetProfile,
    private val getTeamsUseCase: GetTeams,
    private val getDriversUseCase: GetDrivers
): ViewModel() {
    private var _state = MutableStateFlow<ProfileEditState>(ProfileEditState.Loading)
    val state: StateFlow<ProfileEditState> = _state

    fun getProfile() {
        viewModelScope.launch {
            _state.value = ProfileEditState.Loading
            val result = withContext(Dispatchers.IO) {
                getProfileUseCase().fold(
                    onSuccess = { user ->
                        val teams = getTeamsUseCase().getOrNull() ?: emptyList()
                        val drivers = getDriversUseCase().getOrNull() ?: emptyList()
                        ProfileEditState.Ready(user, teams, drivers)
                    },
                    onFailure = {
                        ProfileEditState.Error("Ha ocurrido un error, intentelo más tarde")
                    }
                )
            }
            _state.value = result
        }
    }

    fun editProfile(user: User) {
        viewModelScope.launch {
            _state.value = ProfileEditState.Loading
            val result = withContext(Dispatchers.IO) {
                editProfileUseCase(user)
            }
            if (!result.isSuccess) {
                _state.value = ProfileEditState.Error("Error al editar perfil")
            } else {
                _state.value = ProfileEditState.Success
            }
        }
    }

}