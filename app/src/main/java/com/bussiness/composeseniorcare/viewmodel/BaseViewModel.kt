package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.NetworkUtils
import com.bussiness.composeseniorcare.util.UiState
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(
    private val app: Application
) : AndroidViewModel(app) {

    private val _uiState = mutableStateOf<UiState<T>>(UiState.Idle)
    val uiState: State<UiState<T>> = _uiState

    protected fun launchRequest(request: suspend () -> T) {
        viewModelScope.launch {
            if (!NetworkUtils.isOnline(app)) {
                _uiState.value = UiState.NoInternet
                return@launch
            }

            _uiState.value = UiState.Loading

            try {
                val result = request()
                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: ErrorMessage.CATCH_ERROR)
            }
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
