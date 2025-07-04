package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.CommonResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyOTPViewModel  @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<CommonResponseModel>(app) {

    fun verifyOtpApi(email: String,otp:String) {
        launchFlowWithKey("verify",repository.verifyOTPApi(email,otp))
    }

    fun resendOTPApi(email: String) {
        launchFlowWithKey("resend",repository.forgotPasswordApi(email))
    }
}