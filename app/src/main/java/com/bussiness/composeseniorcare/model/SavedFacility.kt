package com.bussiness.composeseniorcare.model

data class SavedFacility(
    val facility_id: Int = 0,
    val facility_name: String = "",
    val image: String = "",
    val location: String = "",
    val price: String = "",
    val saved: String = "",
    val services: String = ""
)
