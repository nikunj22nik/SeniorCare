package com.bussiness.composeseniorcare.model

data class FacilityListResponse(
    val code: Int = 0,
    val data: Data ,
    val message: String = "",
    val status: Boolean = false
)
