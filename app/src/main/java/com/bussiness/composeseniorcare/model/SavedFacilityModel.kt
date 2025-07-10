package com.bussiness.composeseniorcare.model

data class SavedFacilityModel(
    val code: Int? = null,
    val message: String? = null,
    val savedFacilities: List<SavedFacility> = emptyList(),
    val status: Boolean = false
)
