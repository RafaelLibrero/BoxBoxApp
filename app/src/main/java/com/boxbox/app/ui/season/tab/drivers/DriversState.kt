package com.boxbox.app.ui.season.tab.drivers

import com.boxbox.app.domain.model.Driver

sealed class DriversState {
    object Loading : DriversState()
    data class Success(val drivers: List<Driver>) : DriversState()
    data class Error(val message: String) : DriversState()
}