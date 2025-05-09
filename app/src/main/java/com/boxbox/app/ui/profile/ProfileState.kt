package com.boxbox.app.ui.profile

import com.boxbox.app.domain.model.User

sealed class ProfileState {
    object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String): ProfileState()
}