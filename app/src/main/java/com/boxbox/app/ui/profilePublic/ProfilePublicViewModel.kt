package com.boxbox.app.ui.profilePublic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.model.ProfileData
import com.boxbox.app.domain.usecase.GetDriver
import com.boxbox.app.domain.usecase.GetTeam
import com.boxbox.app.domain.usecase.GetUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfilePublicViewModel @Inject constructor(
    private val getUserUseCase: GetUser,
    private val getTeamUseCase: GetTeam,
    private val getDriverUseCase: GetDriver
) : ViewModel() {
    private var _state = MutableStateFlow<ProfilePublicState>(ProfilePublicState.Loading)
    val state: StateFlow<ProfilePublicState> = _state

    fun getProfile(id: Int) {
        viewModelScope.launch {
            _state.value = ProfilePublicState.Loading
            val result = withContext(Dispatchers.IO) {
                val profileResult = getUserUseCase(id)

                profileResult.fold(
                    onSuccess = { user ->
                        val team = user.teamId?.let { getTeamUseCase(it).getOrNull() }
                        val driver = user.driverId?.let { getDriverUseCase(it).getOrNull() }
                        ProfilePublicState.Success(ProfileData(user, team, driver))
                    },
                    onFailure = {
                        ProfilePublicState.Error("Ha ocurrido un error, intentelo más tarde")
                    }
                )
            }
            _state.value = result
        }
    }
}