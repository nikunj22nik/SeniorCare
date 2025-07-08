package com.bussiness.composeseniorcare.apiservice

import com.bussiness.composeseniorcare.model.CommonResponseModel
import com.bussiness.composeseniorcare.model.LoginResponse
import com.bussiness.composeseniorcare.model.ProfileModel
import com.bussiness.composeseniorcare.model.Register
import com.bussiness.composeseniorcare.util.UiState
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body

interface ApiRepository {

    fun loginApiRequest(emailOrPhone: String, password: String, deviceType: String): Flow<UiState<LoginResponse>>

    fun forgotPasswordApi(emailOrPhone: String): Flow<UiState<CommonResponseModel>>

    fun verifyOTPApi(emailOrPhone: String, otp: String): Flow<UiState<CommonResponseModel>>

    fun registerUser(@Body register: Register) :Flow<UiState<Pair<String,Int>>>

    fun resendVerifyOTPApi(emailOrPhone: String): Flow<UiState<CommonResponseModel>>

    fun createPasswordApi(emailOrPhone: String, password: String): Flow<UiState<CommonResponseModel>>

    fun logoutApi(id: Int): Flow<UiState<CommonResponseModel>>

    fun getProfileApi(id: String): Flow<UiState<ProfileModel>>

    fun editProfileApi(id: RequestBody, name: RequestBody, email: RequestBody, phone: RequestBody, location: RequestBody, profileImage: MultipartBody.Part?): Flow<UiState<ProfileModel>>

    fun sendPhoneOtpApi(phone: String): Flow<UiState<CommonResponseModel>>

    fun verifyPhoneOtpApi(otp: String, phone: String): Flow<UiState<CommonResponseModel>>

    fun contactUsApi(name: String, email: String, phone: String, message: String): Flow<UiState<CommonResponseModel>>

}
