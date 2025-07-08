package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.CommonResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<CommonResponseModel>(app) {

    fun forgotPasswordApi(email: String) {
        launchFlow(repository.forgotPasswordApi(email))
    }
}