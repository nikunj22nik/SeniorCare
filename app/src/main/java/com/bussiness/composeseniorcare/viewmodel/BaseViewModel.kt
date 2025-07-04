package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.composeseniorcare.util.NetworkUtils
import com.bussiness.composeseniorcare.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(
    private val app: Application
) : AndroidViewModel(app) {

    private val _uiState = mutableStateOf<UiState<T>>(UiState.Idle)
    val uiState: State<UiState<T>> = _uiState

    protected fun launchFlow(flowRequest: Flow<UiState<T>>) {
        viewModelScope.launch {
            if (!NetworkUtils.isOnline(app)) {
                _uiState.value = UiState.NoInternet
                return@launch
            }

            flowRequest.collectLatest { state ->
                _uiState.value = state
            }
        }
    }

    fun resetState() {
        _uiState.value = UiState.Idle
    }

    // Multi-state map with lazy init
    private val stateMap = mutableMapOf<String, MutableState<UiState<T>>>()
    // Use this when managing multiple flows (e.g., verifyOtp, resendOtp)
    protected fun launchFlowWithKey(key: String, flowRequest: Flow<UiState<T>>) {
        val keyState = stateMap.getOrPut(key) { mutableStateOf(UiState.Idle as UiState<T>) }

        viewModelScope.launch {
            if (!NetworkUtils.isOnline(app)) {
                keyState.value = UiState.NoInternet
                return@launch
            }

            flowRequest.collectLatest { result ->
                keyState.value = result
            }
        }
    }


    // Read state from key
    fun getUiState(key: String): State<UiState<T>> {
        return stateMap.getOrPut(key) { mutableStateOf(UiState.Idle as UiState<T>) }
    }

    // Reset specific keyed state
    fun resetState(key: String) {
        stateMap[key]?.value = UiState.Idle
    }
}

