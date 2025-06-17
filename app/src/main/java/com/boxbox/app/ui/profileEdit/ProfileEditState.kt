package com.boxbox.app.ui.profileEdit

import com.boxbox.app.domain.model.Driver
import com.boxbox.app.domain.model.Team
import com.boxbox.app.domain.model.User

sealed class ProfileEditState {
    object Loading : ProfileEditState()

    data class Ready(
        val user: User,
        val teams: List<Team>,
        val drivers: List<Driver>
    ) : ProfileEditState()

    object Success : ProfileEditState()

    data class Error(val message: String) : ProfileEditState()
}