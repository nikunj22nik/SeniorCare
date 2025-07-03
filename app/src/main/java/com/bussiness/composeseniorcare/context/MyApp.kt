package com.bussiness.composeseniorcare.context

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize anything you want globally here, like logging, crash reporting, etc.
        // For example:
        // Timber.plant(Timber.DebugTree())
    }
}
