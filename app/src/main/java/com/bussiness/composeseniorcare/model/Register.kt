package com.bussiness.composeseniorcare.model

data class Register(
    val device_type: String ="android",
    val email_or_phone: String ="",
    val name: String ="",
    val password: String =""
)