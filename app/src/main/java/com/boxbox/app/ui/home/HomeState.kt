package com.boxbox.app.ui.home

import com.boxbox.app.domain.model.VTopic

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val topics: List<VTopic>) : HomeState()
    data class Error(val message: String) : HomeState()
}
