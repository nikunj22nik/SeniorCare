package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.CommonResponseModel
import javax.inject.Inject

class VerifyOTPViewModel  @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<CommonResponseModel>(app) {

    fun verifyOtpApi(email: String,otp:String) {
        launchFlow(repository.verifyOTPApi(email,otp))
    }
}