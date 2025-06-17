package com.boxbox.app.ui.season.tab.races

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetRaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RacesViewModel @Inject constructor(private val getRacesUseCase: GetRaces) : ViewModel() {
    private var _state = MutableStateFlow<RacesState>(RacesState.Loading)
    val state: StateFlow<RacesState> = _state

    fun getRaces() {
        viewModelScope.launch {
            _state.value = RacesState.Loading
            val result = withContext(Dispatchers.IO) {
                getRacesUseCase()
            }
            result.fold(
                onSuccess = { races ->
                    _state.value = RacesState.Success(races)
                },
                onFailure = { error ->
                    _state.value = RacesState.Error("Ha ocurrido un error: ${error.message}")
                }
            )
        }
    }
}