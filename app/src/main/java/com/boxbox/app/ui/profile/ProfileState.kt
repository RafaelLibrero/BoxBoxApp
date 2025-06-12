package com.boxbox.app.ui.profile

import com.boxbox.app.domain.model.ProfileData

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val data: ProfileData) : ProfileState()
    data class Error(val message: String): ProfileState()
}