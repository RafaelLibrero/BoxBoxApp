package com.boxbox.app.ui.profilePublic

import com.boxbox.app.domain.model.ProfileData

sealed class ProfilePublicState {
    object Loading : ProfilePublicState()
    data class Success(val data: ProfileData) : ProfilePublicState()
    data class Error(val message: String): ProfilePublicState()
}