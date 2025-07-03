package com.bussiness.composeseniorcare.apiservice

import com.bussiness.composeseniorcare.model.CommonResponseModel
import com.bussiness.composeseniorcare.model.LoginResponse
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.UiState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiListing
) : ApiRepository {


    override fun loginApiRequest(
        emailOrPhone: String,
        password: String,
        deviceType: String
    ): Flow<UiState<LoginResponse>> = flow {
        emit(UiState.Loading)
        try {
            val response = api.loginApiRequest(emailOrPhone, password, deviceType)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    emit(UiState.Success(body)) //  Only emit if status == true
                } else {
                    emit(UiState.Error(body?.message ?: ErrorMessage.API_ERROR))
                }
            } else {
                emit(UiState.Error(response.message() ?: ErrorMessage.SERVER_ERROR))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: ErrorMessage.CATCH_ERROR))
        }
    }

    override fun forgotPasswordApi(emailOrPhone: String): Flow<UiState<CommonResponseModel>> = flow {
        emit(UiState.Loading)
        try {
            val response = api.forgotPasswordApi(emailOrPhone)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    emit(UiState.Success(body)) //  Only emit if status == true
                } else {
                    emit(UiState.Error(body?.message ?: ErrorMessage.API_ERROR))
                }
            } else {
                emit(UiState.Error(response.message() ?: ErrorMessage.SERVER_ERROR))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: ErrorMessage.CATCH_ERROR))
        }
    }

    override fun verifyOTPApi(emailOrPhone: String, otp: String): Flow<UiState<CommonResponseModel>> = flow{
        emit(UiState.Loading)
        try {
            val response = api.verifyOTPApi(emailOrPhone,otp)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    emit(UiState.Success(body)) //  Only emit if status == true
                } else {
                    emit(UiState.Error(body?.message ?: ErrorMessage.API_ERROR))
                }
            } else {
                emit(UiState.Error(response.message() ?: ErrorMessage.SERVER_ERROR))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: ErrorMessage.CATCH_ERROR))
        }
    }


}
