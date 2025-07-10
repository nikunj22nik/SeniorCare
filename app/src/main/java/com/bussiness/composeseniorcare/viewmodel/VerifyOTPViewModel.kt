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

    fun sendPhoneOtp(phone: String) {
        launchFlowWithKey("sendPhoneOtp",repository.sendPhoneOtpApi(phone))
    }

    fun verifyPhoneOtp(otp: String,phone: String) {
        launchFlowWithKey("verifyPhoneOtp",repository.verifyPhoneOtpApi(otp,phone))
    }

    fun sendEmailOtp(email: String) {
        launchFlowWithKey("sendEmailOtp",repository.sendOtpToEmailApi(email))
    }

    fun verifyEmailOtp(otp: String,email: String) {
        launchFlowWithKey("verifyEmailOtp",repository.verifyEmailOtpApi(otp,email))
    }

    fun logoutApi(id: Int) {
        launchFlow(repository.logoutApi(id))
    }
}