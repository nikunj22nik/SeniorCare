package com.bussiness.composeseniorcare.ui.screen.authflow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.HeadingText
import com.bussiness.composeseniorcare.ui.component.PasswordInput
import com.bussiness.composeseniorcare.ui.component.SubmitButton
import com.bussiness.composeseniorcare.ui.theme.BackColor
import com.bussiness.composeseniorcare.ui.theme.Poppins
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.util.ErrorMessage

@Composable
fun CreatePasswordScreen(navController: NavHostController) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEFF1))
    ) {
        // Top Image
        Image(
            painter = painterResource(id = R.drawable.image_3),
            contentDescription = "House Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.FillBounds
        )

        // Bottom Card
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
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Create a New Password",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        color = Purple,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Your new password should be secure and\n easy to remember. Please enter and\n confirm your new password below.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        color = Color(0xFF808080),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(25.dp))

                HeadingText(text = "New Password")
                Spacer(modifier = Modifier.height(8.dp))
                PasswordInput(password = password, onPasswordChange = { password = it })
                Spacer(modifier = Modifier.height(15.dp))
                HeadingText(text = "Confirm Password")
                Spacer(modifier = Modifier.height(8.dp))
                PasswordInput(password = confirmPassword, onPasswordChange = { confirmPassword = it })

                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                SubmitButton(
                    text = "Submit",
                    onClick = {
                        val error = validatePasswords(password, confirmPassword)
                        if (error != null) {
                            errorMessage.value = error
                            return@SubmitButton
                        }

                        // Proceed to login screen
                        errorMessage.value = ""
                        navController.navigate(Routes.LOGIN)
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

fun validatePasswords(password: String, confirmPassword: String): String? {
    if (password.isBlank() || confirmPassword.isBlank()) return ErrorMessage.EMPTY_PASSWORD
    if (password != confirmPassword) return ErrorMessage.MATCH_PASSWORD
    return null
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreatePasswordScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        CreatePasswordScreen(navController = navController)
    }
}
