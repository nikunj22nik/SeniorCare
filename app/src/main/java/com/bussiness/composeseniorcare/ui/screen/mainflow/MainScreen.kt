package com.bussiness.composeseniorcare.ui.screen.mainflow

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.DrawerMenuItem
import com.bussiness.composeseniorcare.navigation.BottomNavGraph
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.CustomBottomBar
import com.bussiness.composeseniorcare.ui.component.SkippedFormat
import com.bussiness.composeseniorcare.ui.component.StatusDialog
import com.bussiness.composeseniorcare.ui.component.bottomNavItems
import com.bussiness.composeseniorcare.ui.component.savedFacilities
import com.bussiness.composeseniorcare.util.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(authNavController: NavHostController) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    val bottomBarRoots = listOf(
        Routes.HOME_SCREEN,
        Routes.COMPARE_FACILITY,
        Routes.SAVED_FACILITIES,
        Routes.PROFILE_SCREEN
    )

    ModalDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            DrawerContent(
                drawerState = drawerState,
                scope = scope,
                onMenuItemClick = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onLogoutClick = {
                    scope.launch { drawerState.close() }
                    showLogoutDialog = true
                },
                sessionManager = sessionManager,
                authNavController = authNavController
            )
            if (showLogoutDialog) {
                StatusDialog(
                    onDismiss = { showLogoutDialog = false },
                    onYesClick = {
                        showLogoutDialog = false
                        // Clear session or preferences if needed
                        sessionManager.clearSession()
                        authNavController.navigate(Routes.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onNoClick = { showLogoutDialog = false },
                    description = "Logout",
                    content = "Are you sure you want to logout?",
                    icon = R.drawable.logout_ic,
                    lButtonText = "Yes",
                    rButtonText = "No"
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                if (bottomBarRoots.contains(currentRoute)) {
                    val updatedBottomNavItems = bottomNavItems.map {
                        if (it.route == Routes.PROFILE_SCREEN && sessionManager.isSkippedLogin()) {
                            it.copy(label = "Login/SignUp")
                        } else {
                            it
                        }
                    }
                    CustomBottomBar(
                        navController = navController,
                        items = updatedBottomNavItems,
                        selectedRoute = currentRoute ?: Routes.HOME_SCREEN,
                        onItemClick = { item ->
                            if (item.route == Routes.PROFILE_SCREEN && sessionManager.isSkippedLogin()) {
                                authNavController.navigate(Routes.LOGIN) {
                                    launchSingleTop = true
                                }
                            } else {
                                navController.navigate(item.route) {
                                    popUpTo(0) { inclusive = true } //  clear entire back stack
                                    launchSingleTop = true          // avoid duplicate
                                }
                            }
                        }

                    )

                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()) {

                // custom menu icon for opening the drawer
                IconButton(
                    onClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }

                // Navigation graph
                BottomNavGraph(
                    navController = navController,
                    authNavController = authNavController,
                    savedFacilities = savedFacilities,
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            }
        }
    }
}

@Composable
fun DrawerContent(
    drawerState: DrawerState,
    scope: CoroutineScope,
    onMenuItemClick: (String) -> Unit,
    onLogoutClick: () -> Unit,
    sessionManager: SessionManager,
    authNavController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Icon(
            painter = painterResource(R.drawable.close_ic),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 10.dp, top = 10.dp)
                .clickable { scope.launch { drawerState.close() } }
        )

        Spacer(Modifier.height(10.dp))
        // Profile Picture and Name (SKIPPED LOGIN)
        if (sessionManager.isSkippedLogin()){
            Image(
                painter = painterResource(id = R.drawable.n_img_ic),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(15.dp))

            SkippedFormat(
                onLoginClick =  { authNavController.navigate(Routes.LOGIN) },
                onSignUpClick = { authNavController.navigate(Routes.SIGN_UP) }
            )
            Spacer(Modifier.height(25.dp))

        }else{
            // Profile Picture and Name (LOGIN)
            Image(
                painter = painterResource(id = R.drawable.draw_img),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Enna Smith",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF575E61),
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp)
            )

            Spacer(Modifier.height(15.dp))
        }

        val menuItems = buildList {
            add(DrawerMenuItem("About Us", R.drawable.about_us_ic, Routes.ABOUT_US))
            add(DrawerMenuItem("Services", R.drawable.services_ic, Routes.SERVICES))
            add(DrawerMenuItem("Subscription Plans", R.drawable.subscription_ic, Routes.SUBSCRIPTIONS))
            add(DrawerMenuItem("Contact Us", R.drawable.contact_ic, Routes.CONTACTUS))
            add(DrawerMenuItem("Compare Facility", R.drawable.vaadin_workplace, Routes.COMPARE_FACILITY))
            add(DrawerMenuItem("Privacy Policy", R.drawable.acc_privacy_ic, route = "${Routes.PRIVACY}/Privacy Policy"))

            if (!sessionManager.isSkippedLogin()) {
                add(DrawerMenuItem("FAQ", R.drawable.faq_ic, Routes.FAQ))
                add(DrawerMenuItem("Logout", R.drawable.logout_ham))  // route can be null or omitted
            }
        }


        menuItems.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch { drawerState.close() }
                        if (item.label == "Logout") {
                            onLogoutClick()
                        } else {
                            item.route?.let { onMenuItemClick(it) }
                        }
                    }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = item.icon),
                    contentDescription = item.label,
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.label,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
            }
        }
    }
}


