package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<LoginResponse>(app) {

    fun login(email: String, password: String) {
        launchFlow(repository.loginApiRequest(email, password, "android"))
    }
}
