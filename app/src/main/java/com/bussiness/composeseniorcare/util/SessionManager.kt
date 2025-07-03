package com.bussiness.composeseniorcare.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SessionManager(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Call this when user successfully logs in
    fun setLogin(value: Boolean) {
        preferences.edit().apply {
            putBoolean(ErrorMessage.KEY_IS_LOGGED_IN, value)
            if (value) {
                putBoolean(ErrorMessage.KEY_IS_SKIP_LOGIN, false) // reset skip status
            }
            apply()
        }
    }

    // Call this when user skips login from onboarding
    fun setSkipLogin(value: Boolean) {
        preferences.edit().apply {
            putBoolean(ErrorMessage.KEY_IS_SKIP_LOGIN, value)
            if (value) {
                putBoolean(ErrorMessage.KEY_IS_LOGGED_IN, false) // reset login status
            }
            apply()
        }
    }

    fun isLoggedIn(): Boolean = preferences.getBoolean(ErrorMessage.KEY_IS_LOGGED_IN, false)

    fun isSkippedLogin(): Boolean = preferences.getBoolean(ErrorMessage.KEY_IS_SKIP_LOGIN, false)

    fun clearSession() {
        preferences.edit { clear() }
    }
}
