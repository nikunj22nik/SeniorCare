package com.bussiness.composeseniorcare.viewmodel.forgotpassword

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.CommonResponseModel
import com.bussiness.composeseniorcare.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<CommonResponseModel>(app) {

    fun forgotPasswordApi(email: String) {
        launchFlow(repository.forgotPasswordApi(email))
    }
}