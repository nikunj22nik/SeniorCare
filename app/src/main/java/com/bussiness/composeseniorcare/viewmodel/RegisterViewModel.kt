package com.bussiness.composeseniorcare.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.Register
import com.bussiness.composeseniorcare.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(Register())
    val uiState: StateFlow<Register> = _uiState

    private val _registerState = MutableStateFlow<UiState<Pair<String, Int>>>(UiState.Idle)
    val registerState: StateFlow<UiState<Pair<String, Int>>> = _registerState


    fun onEmailChanged(value: String) {
        _uiState.value = _uiState.value.copy(email_or_phone = value)
    }

    fun onPasswordChanged(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }


    fun registerUser() {
        viewModelScope.launch {
            repository.registerUser(uiState.value)
                .catch { e-> _registerState.value = UiState.Error(e.message ?: "Unknown error") }
                .collect { result ->
                    _registerState.value = result
                }
        }
    }

}