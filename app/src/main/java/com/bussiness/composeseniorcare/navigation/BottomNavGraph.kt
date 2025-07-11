package com.bussiness.composeseniorcare.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bussiness.composeseniorcare.navigation.Routes.FAQ
import com.bussiness.composeseniorcare.ui.screen.mainflow.CompareFacilities
import com.bussiness.composeseniorcare.ui.screen.mainflow.FacilityListing
import com.bussiness.composeseniorcare.ui.screen.mainflow.HomeScreen
import com.bussiness.composeseniorcare.ui.screen.mainflow.ListingDetail
import com.bussiness.composeseniorcare.ui.screen.mainflow.ProfileScreen
import com.bussiness.composeseniorcare.ui.screen.mainflow.SavedFacilities
import com.bussiness.composeseniorcare.ui.screen.sidenavmenu.AboutUs
import com.bussiness.composeseniorcare.ui.screen.sidenavmenu.ContactUs
import com.bussiness.composeseniorcare.ui.screen.sidenavmenu.DeleteAccount
import com.bussiness.composeseniorcare.ui.screen.sidenavmenu.FAQScreen
import com.bussiness.composeseniorcare.ui.screen.sidenavmenu.ServicesScreen
import com.bussiness.composeseniorcare.ui.screen.sidenavmenu.SubscriptionPlan


@Composable
fun BottomNavGraph(
    navController: NavHostController,
    authNavController: NavHostController,
    onOpenDrawer: () -> Unit
) {
    NavHost(navController, startDestination = Routes.HOME_SCREEN) {
        composable(Routes.HOME_SCREEN) { HomeScreen(authNavController,navController,onOpenDrawer) }
        composable(Routes.COMPARE_FACILITY) { CompareFacilities(navController,onOpenDrawer) }
        composable(Routes.SAVED_FACILITIES) { SavedFacilities(navController, onOpenDrawer) }
        composable(Routes.PROFILE_SCREEN) { ProfileScreen(navController) }
        composable(Routes.FACILITY_LISTING) { FacilityListing(navController,onOpenDrawer, "") }
        composable(Routes.LISTING_DETAIL) { ListingDetail(navController,onOpenDrawer) }
        composable(Routes.ABOUT_US) { AboutUs(navController, title = "About Us") }
        composable(Routes.SERVICES) { ServicesScreen(navController) }
        composable(Routes.SUBSCRIPTIONS) { SubscriptionPlan(navController) }
        composable(Routes.CONTACTUS) { ContactUs(navController) }
        composable(FAQ) { FAQScreen(navController) }
        composable(Routes.DELETE_ACCOUNT) { DeleteAccount(navController) }
        composable(
            route = "${Routes.PRIVACY}/{title}",
            arguments = listOf(navArgument("title") { type = NavType.StringType })
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: "Privacy Policy"
            AboutUs(navController, title)
        }
        composable(
            route = Routes.FACILITY_LISTING_WITH_ARG,
            arguments = listOf(navArgument("type") { type = NavType.StringType })
        ) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: ""
            FacilityListing(type = type, navController = navController, onOpenDrawer = onOpenDrawer)
        }


    }
}
