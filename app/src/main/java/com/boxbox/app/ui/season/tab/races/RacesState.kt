package com.boxbox.app.ui.season.tab.races

import com.boxbox.app.domain.model.Race

sealed class RacesState {
    object Loading : RacesState()
    data class Success(val races: List<Race>) : RacesState()
    data class Error(val message: String) : RacesState()
}