package com.bussiness.composeseniorcare.data.model

data class SubscriptionPlan(
    val name: String,
    val price: String,
    val features: List<String>,
    val status: String
)



