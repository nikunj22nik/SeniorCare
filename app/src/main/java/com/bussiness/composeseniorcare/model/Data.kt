package com.bussiness.composeseniorcare.model

data class Data(
    val email: String? = null,
    val id: Int? = null,
    val location: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val profileImage: String? = null,
    val answer: String? = null,
    val created_at: String? = null,
    val question: String? = null,
    val updated_at: String? = null,
    val facility_list: List<Facility> = emptyList()
)
