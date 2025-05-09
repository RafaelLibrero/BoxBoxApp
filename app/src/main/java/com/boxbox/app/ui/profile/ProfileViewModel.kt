package com.boxbox.app.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getProfileUseCase: GetProfile) : ViewModel() {
    private var _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state: StateFlow<ProfileState> = _state

    fun getProfile() {
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            val result = withContext(Dispatchers.IO) {
                getProfileUseCase()
            }
            if (result!= null) {
                _state.value = ProfileState.Success(result)
            }else {
                _state.value = ProfileState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }

}