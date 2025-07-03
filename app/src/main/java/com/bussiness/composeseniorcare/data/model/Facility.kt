package com.bussiness.composeseniorcare.data.model

data class Facility(
    val imageResId: Int,
    val name: String,
    val location: String,
    val services: String,
    val price: String,
    val rating: String,
    val isBookmarked: Boolean
)

