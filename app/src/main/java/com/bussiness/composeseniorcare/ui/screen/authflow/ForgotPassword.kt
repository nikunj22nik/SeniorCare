package com.bussiness.composeseniorcare.ui.screen.authflow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement.Top
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
import com.bussiness.composeseniorcare.ui.component.EmailOrPhoneInput
import com.bussiness.composeseniorcare.ui.component.SubmitButton
import com.bussiness.composeseniorcare.ui.theme.BackColor
import com.bussiness.composeseniorcare.ui.theme.Poppins
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.util.ErrorMessage

@Composable
fun ForgotPasswordScreen(
    navController: NavHostController,
    ) {
    var input by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAEFF1))
    ) {
        // Top image
        Image(
            painter = painterResource(id = R.drawable.image_2),
            contentDescription = "House Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            contentScale = ContentScale.FillBounds
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
                verticalArrangement = Top,
            ) {
                Text(
                    text = "Forgot Your Password?",
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
                    text = "Please enter your Email/Phone Number to\n get a verification code.",
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
                    text = "Email/Phone Number",
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
                    onValueChange = {input = it}
                )
                if (showError) {
                    Text(
                        text = ErrorMessage.INLINE_ERROR_EMAIL,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                SubmitButton(text = "Send Code", onClick = {
                    if (input.isNotBlank()) {
                        showError = false
                        navController.navigate(Routes.VERIFY_OTP)
                    } else {
                        showError = true
                    }
                })


            }
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ForgotPasswordScreenPreview() {
    val navController = rememberNavController() // from androidx.navigation.compose
    MaterialTheme {
        ForgotPasswordScreen(
            navController = navController
        )
    }
}




