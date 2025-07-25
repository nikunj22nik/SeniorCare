package com.bussiness.composeseniorcare.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bussiness.composeseniorcare.ui.intro.OnboardingScreen
import com.bussiness.composeseniorcare.ui.screen.mainflow.MainScreen
import com.bussiness.composeseniorcare.ui.intro.SplashScreen
import com.bussiness.composeseniorcare.ui.screen.authflow.CreatePasswordScreen
import com.bussiness.composeseniorcare.ui.screen.authflow.ForgotPasswordScreen
import com.bussiness.composeseniorcare.ui.screen.authflow.LoginScreen
import com.bussiness.composeseniorcare.ui.screen.authflow.SignUpScreen
import com.bussiness.composeseniorcare.ui.screen.authflow.VerifyOTP
import com.bussiness.composeseniorcare.ui.screen.sidenavmenu.AboutUs

@Composable
fun NavGraph(authNavController: NavHostController) {
    NavHost(navController = authNavController, startDestination = Routes.SPLASH) {

        composable(Routes.LOGIN) { LoginScreen(authNavController) }
        composable(Routes.SPLASH) { SplashScreen(authNavController) }
        composable(Routes.MAIN_SCREEN){ MainScreen(authNavController) }
        composable(Routes.ONBOARDING) { OnboardingScreen(authNavController) }
        composable(Routes.FORGOT_PASSWORD) { ForgotPasswordScreen(authNavController) }
        composable(Routes.ABOUT_US){ AboutUs(navController = authNavController, title = "About Us")}
        composable(Routes.SIGN_UP) { val checkedState = remember { mutableStateOf(false) }
            SignUpScreen(
                navController = authNavController,
                onLoginClick = { authNavController.navigate(Routes.LOGIN) },
                onTermsClick = { authNavController.navigate(Routes.ABOUT_US) },
                onPrivacyClick = { authNavController.navigate(Routes.ABOUT_US) },
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
        }
        composable(
            route = "${Routes.VERIFY_OTP}/{input}",
            arguments = listOf(navArgument("input") { type = NavType.StringType })
        ) { backStackEntry ->
            val input = backStackEntry.arguments?.getString("input") ?: ""
            VerifyOTP(navController = authNavController,input)
        }
        composable(
            route = "${Routes.CREATE_PASSWORD}/{input}",
            arguments = listOf(navArgument("input") { type = NavType.StringType })
        ) { backStackEntry ->
            val input = backStackEntry.arguments?.getString("input") ?: ""
            CreatePasswordScreen(navController = authNavController,input)
        }
    }
}
