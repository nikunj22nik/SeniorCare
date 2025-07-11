package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.SavedFacilityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SavedFacilityViewModel @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<SavedFacilityModel>(app) {

    fun savedFacilityApi(id:String){
        launchFlow(repository.savedFacilitiesApi(id))
    }
}