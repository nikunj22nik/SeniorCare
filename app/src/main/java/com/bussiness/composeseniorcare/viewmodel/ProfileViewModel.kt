package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.ProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<ProfileModel>(app) {

    fun getProfile(id: String) {
        launchFlowWithKey("getProfile",repository.getProfileApi(id))
    }

    fun editProfile(
        id: String,
        name: String,
        email: String,
        phone: String,
        location: String,
        profileImage: MultipartBody.Part?
    ) {
        launchFlowWithKey(
            "editProfile",
            repository.editProfileApi(
                id.toRequestBody("text/plain".toMediaTypeOrNull()),
                name.toRequestBody("text/plain".toMediaTypeOrNull()),
                email.toRequestBody("text/plain".toMediaTypeOrNull()),
                phone.toRequestBody("text/plain".toMediaTypeOrNull()),
                location.toRequestBody("text/plain".toMediaTypeOrNull()),
                profileImage
            )
        )
    }



}