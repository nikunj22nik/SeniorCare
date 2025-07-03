package com.bussiness.composeseniorcare.apiservice

import android.content.Context
import android.content.Intent
import com.bussiness.composeseniorcare.util.SessionManager

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val sessionManager = SessionManager(context)

        // Add Authorization header if token is available
        sessionManager.getAuthToken()?.let { token ->
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }
        // Add static API key
        //requestBuilder.addHeader("x-api-key", "bGS6lzFqvvSQ8ALbOxatm7/Vk7mLQyzqaS34Q4oR1ew=")
        val response = chain.proceed(requestBuilder.build())
        // Check for 401 Unauthorized response
        if (response.code == 401) {
            handleTokenExpiration(sessionManager)
        }
        return response
    }

    private fun handleTokenExpiration(sessionManager: SessionManager) {
        // Clear session
        sessionManager.logOut()
        // Redirect to login screen

    }

}
