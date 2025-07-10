package com.bussiness.composeseniorcare.model

data class LoginResponse(
    val deviceType: String? = null,
    val message: String? = null,
    val status: Boolean? = null,
    val token: String? = null,
    val data: Data? = null
)

