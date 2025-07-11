package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<LoginResponse>(app) {

    fun register(email: String, password: String) {
        launchFlow(repository.signUpApiRequest(email, password, "android"))
    }
}