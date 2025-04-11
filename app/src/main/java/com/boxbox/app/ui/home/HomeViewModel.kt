package com.boxbox.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boxbox.app.domain.usecase.GetVTopics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getVTopicsUseCase: GetVTopics): ViewModel() {

    private var _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state: StateFlow<HomeState> = _state

    fun getVTopics() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            val result = withContext(Dispatchers.IO) {
                getVTopicsUseCase()
            }
            if(result!=null){
                _state.value = HomeState.Success(result)
            }else{
                _state.value = HomeState.Error("Ha ocurrido un error, intentelo mas tarde")
            }
        }
    }
}