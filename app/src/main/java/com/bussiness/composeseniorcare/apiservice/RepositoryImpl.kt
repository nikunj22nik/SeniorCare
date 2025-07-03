package com.bussiness.composeseniorcare.apiservice

import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiListing
) : ApiRepository {

//    override suspend fun bogyGoal(): Flow<UiState<String>> = flow {
//        emit(UiState.Loading)

//        try {
//            val response = api.getBogyGoal()
//
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    emit(UiState.Success(it.toString()))
//                } ?: emit(UiState.Error(ErrorMessage.apiError))
//            } else {
//                emit(UiState.Error(ErrorMessage.serverError))
//            }
//        } catch (e: Exception) {
//            emit(UiState.Error(ErrorMessage.serverError))
//        }
//    }
}