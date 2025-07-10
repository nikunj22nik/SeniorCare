package com.bussiness.composeseniorcare.viewmodel

import android.app.Application
import com.bussiness.composeseniorcare.apiservice.ApiRepository
import com.bussiness.composeseniorcare.model.FAQModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FAQViewModel  @Inject constructor(app: Application, private val repository: ApiRepository
) : BaseViewModel<FAQModel>(app) {

    fun faqApi(){
        launchFlow(repository.faqApi())
    }
}