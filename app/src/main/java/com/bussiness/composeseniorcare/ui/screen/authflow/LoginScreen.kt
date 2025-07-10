package com.bussiness.composeseniorcare.ui.screen.authflow

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.AppLoader
import com.bussiness.composeseniorcare.ui.component.EmailOrPhoneInput
import com.bussiness.composeseniorcare.ui.component.ErrorDialog
import com.bussiness.composeseniorcare.ui.component.GoogleButtonWithIcon
import com.bussiness.composeseniorcare.ui.component.HeadingText
import com.bussiness.composeseniorcare.ui.component.LoginSuccessDialog
import com.bussiness.composeseniorcare.ui.component.PasswordInput
import com.bussiness.composeseniorcare.ui.component.SubmitButton
import com.bussiness.composeseniorcare.ui.theme.BackColor
import com.bussiness.composeseniorcare.ui.theme.Poppins
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.SessionManager
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    onSignUpClick: () -> Unit = { navController.navigate(Routes.SIGN_UP) },
    onForgotPasswordClick: () -> Unit = { navController.navigate(Routes.FORGOT_PASSWORD) },
    onGoogleLoginClick: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.value

    var input by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var backPressedOnce by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    BackHandler(true) {
        if (backPressedOnce) {
            (context as? Activity)?.finishAffinity()
        } else {
            backPressedOnce = true
            Toast.makeText(context, ErrorMessage.BACK_PRESSED_ONCE, Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                backPressedOnce = false
            }, 2000)
        }
    }

    LaunchedEffect(state) {
        when (state) {
            is UiState.Success -> {
                val response = state.data
                val userId = response.data?.id ?: -1
                val token = response.token ?: ""
                val email = response.data?.email ?: ""

                sessionManager.setLogin(true)
                sessionManager.setSkipLogin(false)
                sessionManager.saveUserId(userId)
                sessionManager.setAuthToken(token)
                sessionManager.saveInput(email)

                showSuccessDialog = true
                viewModel.resetState()
            }

            is UiState.Error -> {
                errorMessage = state.message
                showDialog = true
                viewModel.resetState()
            }

            UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                viewModel.resetState()
            }

            else -> Unit
        }
    }

    if (state is UiState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .zIndex(1f), // to bring it on top if necessary
            contentAlignment = Alignment.Center
        ) {
            AppLoader()
        }
    }



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEFF1))
    ) {

        if (showSuccessDialog) {
            LoginSuccessDialog(
                onDismiss = { showSuccessDialog = false },
                onOkayClick = {
                    showSuccessDialog = false
                    navController.navigate(Routes.MAIN_SCREEN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        if (showDialog) {
            ErrorDialog(
                message = errorMessage,
                onConfirm = { showDialog = false },
                onDismiss = { showDialog = false }
            )
        }


        // Top image
        Image(
            painter = painterResource(id = R.drawable.bg_img),
            contentDescription = "House Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.FillBounds
        )

        // "Skip" Button
        Image(
            painter = painterResource(id = R.drawable.skip_ic),
            contentDescription = "Skip Button",
            modifier = Modifier
                .background(Color.Transparent, shape = CircleShape)
                .padding(horizontal = 12.dp, vertical = 35.dp)
                .align(Alignment.TopStart)
                .clickable {
                    sessionManager.setSkipLogin(true)
                    sessionManager.setLogin(false)
                    navController.navigate(Routes.MAIN_SCREEN)
                }
        )

        // Overlapping Bottom Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.62f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
        ) {
            // Main box with border and background
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(color = BackColor, shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
                    .border(
                        width = 2.dp,
                        color = Purple,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
            )

            // White overlay to hide bottom border (1dp height)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .align(Alignment.BottomCenter)
                    .background(color = BackColor) // Use your actual background color
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        color = Purple,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Log in to your account and explore senior\nliving options tailored to your needs.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = Color(0xFF808080),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    text = "Account",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                EmailOrPhoneInput(
                    value = input,
                    onValueChange = { input = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                HeadingText(text = "Password")

                Spacer(modifier = Modifier.height(10.dp))

                PasswordInput(password = password, onPasswordChange = { password = it })

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Forgot password?",
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { onForgotPasswordClick() },
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                    )
                )

                Spacer(modifier = Modifier.height(15.dp))

                SubmitButton(
                    text = "Login",
                    onClick = {
                        emailError = input.isBlank()
                        passwordError = password.isBlank()

                        if (!emailError && !passwordError) {
                            viewModel.login(input, password)
                        }else {
                            Toast.makeText(context, ErrorMessage.EMPTY_FIELD, Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                )



                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Text(
                        text = "Don’t you have an account?",
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Normal,
                            color = Color.Black
                        )
                    )
                    Text(
                        text = "Sign Up",
                        modifier = Modifier.clickable { onSignUpClick() },
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            fontStyle = FontStyle.Normal,
                            color = Color.Black,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = Color(0xFFD9D9D9)
                    )

                    Text(
                        text = "Or Login with",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.Black
                        )
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp),
                        color = Color(0xFFD9D9D9)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                GoogleButtonWithIcon(
                    text = "Google",
                    iconRes = R.drawable.google_ic,
                    onClick = { onGoogleLoginClick() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 20.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        LoginScreen(
            navController = navController,
            onSignUpClick = {},
            onForgotPasswordClick = {},
            onGoogleLoginClick = {}
        )
    }
}
