package com.bussiness.composeseniorcare.ui.screen.authflow

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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
import com.bussiness.composeseniorcare.ui.component.HeadingText
import com.bussiness.composeseniorcare.ui.component.PasswordInput
import com.bussiness.composeseniorcare.ui.component.SubmitButton
import com.bussiness.composeseniorcare.ui.theme.BackColor
import com.bussiness.composeseniorcare.ui.theme.Poppins
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.SessionManager
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.RegisterViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    onLoginClick: () -> Unit = { navController.navigate(Routes.LOGIN) },
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {

    var input by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val viewModel : RegisterViewModel =  hiltViewModel()
    val state = viewModel.uiState.value

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

                navController.navigate(Routes.MAIN_SCREEN)

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


    if (showDialog) {
        ErrorDialog(
            message = errorMessage,
            onConfirm = { showDialog = false },
            onDismiss = { showDialog = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEFF1))
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_4),
            contentDescription = "House Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.Crop
        )

        // Skip Button
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
                    .background(
                        color = BackColor,
                        shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                    )
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
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Create Your Account",
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
                    text = "Join us today and explore senior living\noptions tailored to your needs.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        color = Color(0xFF808080),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(25.dp))

                HeadingText(text = "Email/Phone Number")
                Spacer(modifier = Modifier.height(8.dp))
                EmailOrPhoneInput(value = input,
                    onValueChange = { input = it }
                )

                Spacer(modifier = Modifier.height(15.dp))

                HeadingText(text = "Password")
                Spacer(modifier = Modifier.height(8.dp))
                PasswordInput(password = password,
                    onPasswordChange = { password = it })

                Spacer(modifier = Modifier.height(15.dp))

                HeadingText(text = "Confirm Password")
                Spacer(modifier = Modifier.height(8.dp))
                PasswordInput(password = confirmPassword,
                    onPasswordChange = { confirmPassword = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(5.dp)
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = onCheckedChange,
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF5C2C4D)
                        )
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    val annotatedString = buildAnnotatedString {
                        append("I agree to the ")

                        pushStringAnnotation(tag = "TERMS", annotation = "terms")
                        withStyle(SpanStyle(color = Purple, textDecoration = TextDecoration.Underline)) {
                            append("Terms & condition")
                        }
                        pop()

                        append(" and ")

                        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
                        withStyle(SpanStyle(color = Purple, textDecoration = TextDecoration.Underline)) {
                            append("Privacy Policy.")
                        }
                        pop()
                    }

                    ClickableText(
                        text = annotatedString,
                        style = TextStyle(
                            color = Color(0xFF808080),
                            fontSize = 12.sp
                        ),
                        onClick = { offset ->
                            annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let { annotation ->
                                when (annotation.tag) {
                                    "TERMS" -> onTermsClick()
                                    "PRIVACY" -> onPrivacyClick()
                                }
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                SubmitButton(
                    text = "Register",
                    onClick = {
                        val emailError = input.isBlank()
                        val passwordError = password.isBlank()
                        val confirmPasswordError = confirmPassword.isBlank()
                        val passwordsMatch = password == confirmPassword

                        val message = when {
                            emailError && passwordError && confirmPasswordError -> "Please fill all details"
                            emailError -> "Please enter email or phone"
                            passwordError -> "Please enter password"
                            confirmPasswordError -> "Please confirm your password"
                            !passwordsMatch -> "Passwords do not match"
                            !checked -> "Please accept terms and conditions"
                            else -> null
                        }

                        if (message != null) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.register(input, confirmPassword)
                        }
                    }
                )


                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already you have an account? ",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "Login",
                        modifier = Modifier.clickable { onLoginClick() },
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Black,
                        textDecoration = TextDecoration.Underline
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignupScreenPreview() {
    val navController = rememberNavController()
    var checked by remember { mutableStateOf(false) }

    SignUpScreen(
        navController = navController,
        onLoginClick = {},
        onTermsClick = {},
        onPrivacyClick = {},
        checked = checked,
        onCheckedChange = { checked = it }
    )
}
