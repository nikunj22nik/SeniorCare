package com.bussiness.composeseniorcare.model

data class LoginResponse(
    val deviceType: String,
    val message: String,
    val status: Boolean,
    val token: String,
    val user: User
)

data class User(
    val email: String,
    val id: Int,
    val name: Any
)