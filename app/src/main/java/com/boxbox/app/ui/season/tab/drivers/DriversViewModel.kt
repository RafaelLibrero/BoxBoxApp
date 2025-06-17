package com.boxbox.app.ui.season.tab.drivers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetDrivers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DriversViewModel @Inject constructor(private val getDriversUseCase: GetDrivers) :
    ViewModel() {

    private var _state = MutableStateFlow<DriversState>(DriversState.Loading)
    val state: StateFlow<DriversState> = _state

    fun getDrivers() {
        viewModelScope.launch {
            _state.value = DriversState.Loading
            val result = withContext(Dispatchers.IO) {
                getDriversUseCase()
            }
            result.fold(
                onSuccess = { drivers ->
                    _state.value = DriversState.Success(drivers)
                },
                onFailure = { error ->
                    _state.value = DriversState.Error("Ha ocurrido un error: ${error.message}")
                }
            )
        }
    }
}