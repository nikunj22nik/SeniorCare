package com.bussiness.composeseniorcare.model

data class ProfileModel(
    val code: Int,
    val `data`: Data,
    val message: String,
    val status: Boolean
)

data class Data(
    val email: String,
    val id: Int,
    val location: String,
    val name: String,
    val phone: String,
    val profileImage: String
)