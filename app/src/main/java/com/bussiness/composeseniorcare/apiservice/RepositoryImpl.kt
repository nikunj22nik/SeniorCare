package com.bussiness.composeseniorcare.apiservice

import com.bussiness.composeseniorcare.model.CommonResponseModel
import com.bussiness.composeseniorcare.model.LoginResponse
import com.bussiness.composeseniorcare.model.Register
import com.bussiness.composeseniorcare.util.AppConstant
import com.bussiness.composeseniorcare.util.ErrorHandler
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
            }  else {
                // Parse error body
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, LoginResponse::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    ErrorMessage.SERVER_ERROR
                }
                emit(UiState.Error(errorMessage))
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
                    emit(UiState.Success(body))
                } else {
                    emit(UiState.Error(body?.message ?: ErrorMessage.API_ERROR))
                }
            } else {
                // Parse error body
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, CommonResponseModel::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    ErrorMessage.SERVER_ERROR
                }
                emit(UiState.Error(errorMessage))
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
            }  else {
                // Parse error body
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, CommonResponseModel::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    ErrorMessage.SERVER_ERROR
                }
                emit(UiState.Error(errorMessage))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: ErrorMessage.CATCH_ERROR))
        }
    }

    override  fun registerUser(register: Register): Flow<UiState<Pair<String, Int>>> = flow {
        try {
            api.registerUser(
                register
            ).apply {
                emit(UiState.Loading)
                if (isSuccessful) {
                    body()?.let { resp ->
                        if (resp.has("status") && resp.get("status").asBoolean) {
                            val token = resp.get("token").asString
                            val userObj = resp.get("user").asJsonObject
                            val userId =userObj.get("id").asInt
                            val p = Pair<String,Int>(token,userId)
                            emit(UiState.Success<Pair<String,Int>>(p))
                        } else {
                            emit(UiState.Error(resp.get("message").asString))
                        }
                    } ?: emit(UiState.Error(AppConstant.unKnownError))
                } else {

                    emit(
                        UiState.Error(
                            ErrorHandler.handleErrorBody(
                                this.errorBody()?.string()
                            )
                        )
                    )
                }
            }
        } catch (e: Exception) {
            emit(UiState.Error(ErrorHandler.emitError(e)))
        }
    }

    override fun resendVerifyOTPApi(emailOrPhone: String): Flow<UiState<CommonResponseModel>> = flow {
        emit(UiState.Loading)
        try {
            val response = api.resendVerifyOTPApi(emailOrPhone)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    emit(UiState.Success(body))
                } else {
                    emit(UiState.Error(body?.message ?: ErrorMessage.API_ERROR))
                }
            }  else {
                // Parse error body
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, CommonResponseModel::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    ErrorMessage.SERVER_ERROR
                }
                emit(UiState.Error(errorMessage))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: ErrorMessage.CATCH_ERROR))
        }
    }

    override fun createPasswordApi(
        emailOrPhone: String,
        password: String
    ): Flow<UiState<CommonResponseModel>> = flow {
        emit(UiState.Loading)
        try {
            val response = api.createPasswordApi(emailOrPhone,password)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null && body.status) {
                    emit(UiState.Success(body))
                } else {
                    emit(UiState.Error(body?.message ?: ErrorMessage.API_ERROR))
                }
            }  else {
                // Parse error body
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(errorBody, CommonResponseModel::class.java)
                    errorResponse.message
                } catch (e: Exception) {
                    ErrorMessage.SERVER_ERROR
                }
                emit(UiState.Error(errorMessage))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: ErrorMessage.CATCH_ERROR))
        }
    }


}
