package com.bussiness.composeseniorcare.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    fun saveUserId(id: Int) {
        preferences.edit { putInt(ErrorMessage.KEY_USER_ID, id) }
    }

    fun saveToken(token: String) {
        preferences.edit { putString(ErrorMessage.KEY_TOKEN, token) }
    }

    fun getUserId(): Int = preferences.getInt(ErrorMessage.KEY_USER_ID, -1)

    fun getToken(): String? = preferences.getString(ErrorMessage.KEY_TOKEN, null)

    fun saveInput(input: String) {
        preferences.edit { putString(ErrorMessage.INPUT, input) }
    }

    fun getSavedInput(): String? = preferences.getString(ErrorMessage.INPUT, null)

    fun clearSession() {
        preferences.edit { clear() }
    }

    // User info
    fun setUserType(userType: String) {
        preferences.edit { putString(AppConstant.USER, userType) }
    }

    fun getUserType(): String? = preferences.getString(AppConstant.USER, "")

    fun setUserVerified(verified: Boolean) {
        preferences.edit { putBoolean(AppConstant.USER_VERIFIED, verified) }
    }

    fun getUserVerified(): Boolean = preferences.getBoolean(AppConstant.USER_VERIFIED, false)

    fun setUserId(id: Int) {
        preferences.edit { putInt(AppConstant.Id, id) }
    }



    fun setNeedMore(value: Boolean) {
        preferences.edit { putBoolean(AppConstant.NEEDMORE, value) }
    }

    fun getNeedMore(): Boolean = preferences.getBoolean(AppConstant.NEEDMORE, false)

    fun setUserSession(session: Boolean) {
        preferences.edit { putBoolean(AppConstant.session, session) }
    }

    fun getUserSession(): Boolean = preferences.getBoolean(AppConstant.session, false)

    fun setAuthToken(token: String) {
        preferences.edit { putString(AppConstant.AuthToken, token) }
    }

    fun getAuthToken(): String? = preferences.getString(AppConstant.AuthToken, "")

    fun setName(name: String) {
        preferences.edit { putString(AppConstant.Name1, name) }
    }

    fun getName(): String? = preferences.getString(AppConstant.Name1, "")

    fun logOut() {
        clearSession()
    }

    fun setCurrentPanel(panel: String) {
        preferences.edit { putString(AppConstant.PANNEL, panel) }
    }

    fun getCurrentPanel(): String = preferences.getString(AppConstant.PANNEL, "") ?: ""

    // Location
    fun setLatitude(latitude: String) {
        preferences.edit { putString(AppConstant.LATITUDE, latitude) }
    }

    fun setLongitude(longitude: String) {
        preferences.edit { putString(AppConstant.LONGITUDE, longitude) }
    }

    fun getLatitude(): String = preferences.getString(AppConstant.LATITUDE, "") ?: ""
    fun getLongitude(): String = preferences.getString(AppConstant.LONGITUDE, "") ?: ""

    fun setGustLatitude(latitude: String) {
        preferences.edit { putString(AppConstant.LATITUDEGUST, latitude) }
    }

    fun setGustLongitude(longitude: String) {
        preferences.edit { putString(AppConstant.LONGITUDEGUST, longitude) }
    }

    fun getGustLatitude(): String = preferences.getString(AppConstant.LATITUDEGUST, "") ?: ""
    fun getGustLongitude(): String = preferences.getString(AppConstant.LONGITUDEGUST, "") ?: ""

    // Chat token
    fun setChatToken(token: String) {
        preferences.edit { putString(AppConstant.CHAT_TOKEN, token) }
    }

    fun getChatToken(): String? = preferences.getString(AppConstant.CHAT_TOKEN, "")

    // Notification toggle
    fun setNotificationOnOffStatus(value: Boolean) {
        preferences.edit { putBoolean(AppConstant.NOTIFICATION, value) }
    }

    fun getNotificationStatus(): Boolean = preferences.getBoolean(AppConstant.NOTIFICATION, false)

    // Filter cache
    fun setFilterRequest(filterRequest: String) {
        preferences.edit { putString(AppConstant.FILTERREQUEST, filterRequest) }
    }

    fun getFilterRequest(): String = preferences.getString(AppConstant.FILTERREQUEST, "") ?: ""

    fun setSearchFilterRequest(filterRequest: String) {
        preferences.edit { putString(AppConstant.SEARCHFILTERREQUEST, filterRequest) }
    }

    fun getSearchFilterRequest(): String = preferences.getString(AppConstant.SEARCHFILTERREQUEST, "") ?: ""

    fun setLoginType(loginType: String) {
        preferences.edit { putString(AppConstant.loginType, loginType) }
    }

    fun getLoginType(): String = preferences.getString(AppConstant.loginType, "") ?: ""

    // Date comparison
    fun isDateGreaterOrEqual(dateStr: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateFormat.isLenient = false
            val inputDate = dateFormat.parse(dateStr)
            val currentDate = dateFormat.parse(dateFormat.format(Date()))
            !inputDate?.before(currentDate)!!
        } catch (e: Exception) {
            false
        }
    }
}
