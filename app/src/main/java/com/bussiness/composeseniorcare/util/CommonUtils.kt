package com.bussiness.composeseniorcare.util

object CommonUtils {

    fun isEmailOrPhone(input: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        val phoneRegex = Regex("^[6-9]\\d{9}$") // Indian 10-digit mobile number starting with 6-9

        return emailRegex.matches(input) || phoneRegex.matches(input)
    }
}