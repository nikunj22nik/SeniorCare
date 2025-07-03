package com.bussiness.composeseniorcare.ui.intro

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.theme.BackColor
import com.bussiness.composeseniorcare.util.SessionManager
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val rippleCount = remember { mutableIntStateOf(0) }
    val scale = remember { Animatable(0.5f) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    LaunchedEffect(rippleCount.intValue) {
        if (rippleCount.intValue < 3) {
            scale.snapTo(0.5f)
            scale.animateTo(
                targetValue = 2f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = FastOutSlowInEasing
                )
            )
            rippleCount.intValue++
        } else {
            delay(200) // small buffer before navigation
            if (sessionManager.isLoggedIn()){
                navController.navigate(Routes.MAIN_SCREEN) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }else{
                navController.navigate(Routes.ONBOARDING) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            }

        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackColor),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .scale(scale.value)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFC3CECD).copy(alpha = 0.4f),
                            Color.Transparent
                        ),
                        radius = 400f
                    ),
                    shape = CircleShape
                )
        )

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(250.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}
