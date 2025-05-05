package com.boxbox.app.ui.season.tab.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetTeams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(private val getTeamsUseCase: GetTeams) : ViewModel() {
    private var _state = MutableStateFlow<TeamsState>(TeamsState.Loading)
    val state: StateFlow<TeamsState> = _state

    fun getRaces() {
        viewModelScope.launch {
            _state.value = TeamsState.Loading
            val result = withContext(Dispatchers.IO) {
                getTeamsUseCase()
            }
            if (result != null) {
                _state.value = TeamsState.Success(result)
            } else {
                _state.value = TeamsState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}