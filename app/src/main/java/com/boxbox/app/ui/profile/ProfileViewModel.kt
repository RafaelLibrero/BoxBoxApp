package com.boxbox.app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.model.ProfileData
import com.boxbox.app.domain.usecase.GetDriver
import com.boxbox.app.domain.usecase.GetProfile
import com.boxbox.app.domain.usecase.GetTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfile,
    private val getTeamUseCase: GetTeam,
    private val getDriverUseCase: GetDriver
) : ViewModel() {
    private var _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state

    fun getProfile() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            val result = withContext(Dispatchers.IO) {
                getProfileUseCase().fold(
                    onSuccess = { user ->
                        val team = user.teamId?.let { getTeamUseCase(it).getOrNull() }
                        val driver = user.driverId?.let { getDriverUseCase(it).getOrNull() }
                        ProfileState.Success(ProfileData(user, team, driver))
                    },
                    onFailure = {
                        ProfileState.Error("Ha ocurrido un error, intentelo más tarde")
                    }
                )
            }
            _state.value = result
        }
    }
}