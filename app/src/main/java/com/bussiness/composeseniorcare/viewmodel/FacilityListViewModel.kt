package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.FacilityListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FacilityListViewModel @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<FacilityListResponse>(app) {

    fun facilityListApi(id:String){
        launchFlow(repository.facilitiesListApi(id))
    }

}