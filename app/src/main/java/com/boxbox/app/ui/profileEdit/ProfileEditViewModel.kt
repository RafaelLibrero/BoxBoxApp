package com.boxbox.app.ui.profileEdit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
            withContext(Dispatchers.IO) {
                val user = getProfileUseCase()
                if (user != null) {
                    val teams = withContext(Dispatchers.IO) {
                        getTeamsUseCase()
                    }
                    val drivers = withContext(Dispatchers.IO) {
                        getDriversUseCase()
                    }
                    _state.value = ProfileEditState.Ready(user, teams, drivers)
                } else {
                    _state.value = ProfileEditState.Error("Ha ocurrido un error, intentelo más tarde")
                }
            }
        }
    }


}