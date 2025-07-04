package com.bussiness.composeseniorcare.ui.screen.authflow

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.AppLoader
import com.bussiness.composeseniorcare.ui.component.ErrorDialog
import com.bussiness.composeseniorcare.ui.component.OtpInputField
import com.bussiness.composeseniorcare.ui.component.SubmitButton
import com.bussiness.composeseniorcare.ui.theme.BackColor
import com.bussiness.composeseniorcare.ui.theme.Poppins
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.VerifyOTPViewModel
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun VerifyOTP(
    navController: NavHostController,
    input: String,
    viewModel: VerifyOTPViewModel = hiltViewModel()
) {
    var otp by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var secondsLeft by remember { mutableIntStateOf(59) }
    var isTimerRunning by remember { mutableStateOf(true) }

    val verifyState = viewModel.getUiState("verify").value
    val resendState = viewModel.getUiState("resend").value


    LaunchedEffect(verifyState, resendState) {
        when {
            verifyState is UiState.Success -> {
                Toast.makeText(navController.context, "OTP verified successfully", Toast.LENGTH_LONG).show()
                navController.navigate("${Routes.CREATE_PASSWORD}/$input")
                showError = false
                viewModel.resetState("verify")
            }

            verifyState is UiState.Error -> {
                errorMessage = verifyState.message
                showDialog = true
                viewModel.resetState("verify")
            }

            verifyState == UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                viewModel.resetState("verify")
            }

            resendState is UiState.Success -> {
                Toast.makeText(navController.context, "OTP resent successfully", Toast.LENGTH_LONG).show()
                viewModel.resetState("resend")
            }

            resendState is UiState.Error -> {
                errorMessage = resendState.message
                showDialog = true
                viewModel.resetState("resend")
            }

            resendState == UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                viewModel.resetState("resend")
            }
        }
    }


    LaunchedEffect(isTimerRunning) {
        if (isTimerRunning) {
            while (secondsLeft > 0) {
                delay(1000L)
                secondsLeft--
            }
            isTimerRunning = false
        }
    }


    if (showDialog) {
        ErrorDialog(
            message = errorMessage,
            onConfirm = { showDialog = false },
            onDismiss = { showDialog = false }
        )
    }

    if (verifyState is UiState.Loading || resendState is UiState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .zIndex(1f),
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
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    text = "Verify Your Account",
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
                    text = "Enter the one-time password (OTP) sent to your email or phone to complete verification.",
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
                    text = "Enter OTP (One-Time-Password)",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                )

                Spacer(modifier = Modifier.height(5.dp))

                // Make sure your OtpInputField has proper width/focus support
                OtpInputField(
                    otpText = otp,
                    onOtpTextChange = { otp = it },
                )

                if (showError) {
                    Text(
                        text = ErrorMessage.INVALID_OTP,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Red,
                            fontSize = 12.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Resend OTP",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = if (isTimerRunning) Color.Gray else Color.Black,
                        modifier = Modifier
                            .then(
                                if (!isTimerRunning) {
                                    Modifier.clickable {
                                        secondsLeft = 59
                                        isTimerRunning = true
                                        viewModel.resendOTPApi(input)
                                    }
                                } else {
                                    Modifier.clickable(enabled = false) {}
                                }
                            )
                    )

                    val timerText = String.format("00:%02d", secondsLeft)

                    Text(
                        text = timerText,
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF808080)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                SubmitButton(
                    text = "Verify OTP",
                    onClick = {
                        if (otp.length == 6 && otp.isNotBlank()){
                            viewModel.verifyOtpApi(input,otp)
                        }else{
                            showError = true
                        }
                         },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun VerifyOTPPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        VerifyOTP (
            navController =navController,
            input = "xyz@gmail.com"

        )
    }
}


