package com.bussiness.composeseniorcare.apiservice

import com.bussiness.composeseniorcare.model.CommonResponseModel
import com.bussiness.composeseniorcare.model.FAQModel
import com.bussiness.composeseniorcare.model.FacilityListResponse
import com.bussiness.composeseniorcare.model.LoginResponse
import com.bussiness.composeseniorcare.model.ProfileModel
import com.bussiness.composeseniorcare.model.Register
import com.bussiness.composeseniorcare.model.SavedFacilityModel
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiListing {

    @FormUrlEncoded
    @POST(ApiEndPoint.LOGIN)
    suspend fun loginApiRequest(
        @Field("email_or_phone") emailOrPhone: String,
        @Field("password") password: String,
        @Field("deviceType") deviceType: String
    ): Response<LoginResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.REGISTER)
    suspend fun signupApiRequest(
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

    @FormUrlEncoded
    @POST(ApiEndPoint.RESEND_OTP)
    suspend fun resendVerifyOTPApi(
        @Field("email_or_phone") emailOrPhone: String,
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.CREATE_PASSWORD)
    suspend fun createPasswordApi(
        @Field("email_or_phone") emailOrPhone: String,
        @Field("password") password: String,
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.LOGOUT)
    suspend fun logoutApi(
        @Field("id") id: Int,
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.GET_PROFILE)
    suspend fun getProfileApi(
        @Field("id") id: String,
    ): Response<ProfileModel>

    @Multipart
    @POST(ApiEndPoint.EDIT_PROFILE)
    suspend fun editProfileApi(
        @Part("id") id: RequestBody,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("location") location: RequestBody,
        @Part profileImage: MultipartBody.Part? // Nullable to allow optional image
    ): Response<ProfileModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.SEND_PHONE_OTP)
    suspend fun sendPhoneOtpApi(
        @Field("phone") phone: String,
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.VERIFY_PHONE)
    suspend fun verifyPhoneOtpApi(
        @Field("otp") otp: String,
        @Field("phone") phone: String
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.CONTACT_US)
    suspend fun contactUsApi(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phone") phone: String,
        @Field("message") message: String,
    ): Response<CommonResponseModel>

    @GET(ApiEndPoint.FAQ)
    suspend fun faqApi(): Response<FAQModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.SEND_OTP_TO_EMAIL)
    suspend fun sendOtpToEmailApi(
        @Field("email") email: String,
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.VERIFY_EMAIL)
    suspend fun verifyEmailOtpApi(
        @Field("otp") otp: String,
        @Field("email") email: String
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.SAVED_FACILITIES)
    suspend fun savedFacilitiesApi(
        @Field("id") id: String,
    ): Response<SavedFacilityModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.FACILITIES_LIST)
    suspend fun facilitiesListApi(
        @Field("id") id: String,
    ): Response<FacilityListResponse>

    @FormUrlEncoded
    @POST(ApiEndPoint.TOGGLE_SAVE_FACILITY)
    suspend fun toggleSaveFacilityApi(
        @Field("id") id: String,
        @Field("f_id") fId: String,
    ): Response<CommonResponseModel>

    @FormUrlEncoded
    @POST(ApiEndPoint.FACILITY_DETAIL)
    suspend fun facilityDetailApi(
        @Field("id") id: String,
        @Field("f_id") fId: String,
    ): Response<CommonResponseModel>


}