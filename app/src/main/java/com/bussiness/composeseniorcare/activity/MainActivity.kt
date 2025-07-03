package com.bussiness.composeseniorcare.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        enableEdgeToEdge() // sets decorFitsSystemWindows = false

        // Make status and navigation bars transparent
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        // Optional: Set light/dark icons on system bars
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true        // light status bar icons (for dark background set false)
        controller.isAppearanceLightNavigationBars = true    // light nav bar icons

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavGraph(authNavController = navController)
            }
        }
    }
}

