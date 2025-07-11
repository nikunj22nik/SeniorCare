package com.bussiness.composeseniorcare.model

data class Facility(
    val facility_name: String = "",
    val f_id: Int = 0,
    val images: List<String> = emptyList(),
    val location: String = "",
    val price: String = "",
    val rating: Any? = null,
    val saved: Int = 0,
    val services: Services,

)
