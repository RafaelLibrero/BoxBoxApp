package com.boxbox.app.ui.season.tab.teams

import com.boxbox.app.domain.model.Team

sealed class TeamsState {
    object Loading : TeamsState()
    data class Success(val teams: List<Team>) : TeamsState()
    data class Error(val message: String) : TeamsState()
}