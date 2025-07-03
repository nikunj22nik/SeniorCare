package com.bussiness.composeseniorcare.apiservice

import com.bussiness.composeseniorcare.model.CommonResponseModel
import com.bussiness.composeseniorcare.model.LoginResponse
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiListing {

    @FormUrlEncoded
    @POST(ApiEndPoint.LOGIN)
    suspend fun loginApiRequest(
        @Field("email_or_phone") emailOrPhone: String,
        @Field("password") password: String,
        @Field("deviceType") deviceType: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.FORGOT_PASSWORD)
    suspend fun forgotPasswordApi(
        @Field("email_or_phone") emailOrPhone: String,
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.VERIFY_OTP)
    suspend fun verifyOTPApi(
        @Field("email_or_phone") emailOrPhone: String,
        @Field("otp") otp: String,
    ): Response<CommonResponseModel>



}