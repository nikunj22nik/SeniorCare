package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.CommonResponseModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonViewModel  @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<CommonResponseModel>(app) {

    fun contactUsApi(name: String,email: String,phone: String,message: String) {
        launchFlowWithKey("contactUs",repository.contactUsApi(name,email,phone,message))
    }

    fun toggleSaveFacilityApi(id: String,fId: String) {
        launchFlowWithKey("toggleSaveFacility",repository.toggleSaveFacilityApi(id,fId))
    }

}