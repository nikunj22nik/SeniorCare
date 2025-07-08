package com.bussiness.composeseniorcare.ui.screen.sidenavmenu

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.bussiness.composeseniorcare.ui.component.ErrorDialog
import com.bussiness.composeseniorcare.ui.component.LongMessageTextBox
import com.bussiness.composeseniorcare.ui.component.SubmitButtonSharp
import com.bussiness.composeseniorcare.ui.component.TopHeadingText
import com.bussiness.composeseniorcare.ui.screen.mainflow.TextSection
import com.bussiness.composeseniorcare.ui.theme.Redish
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.CommonViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.collectAsState


@Composable
fun ContactUs(navController: NavHostController, ) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var isSubmitting by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    val viewModel: CommonViewModel = hiltViewModel()

    val state = viewModel.getUiState("contactUs").value


    LaunchedEffect(state) {
        when (state) {
            is UiState.Success -> {
                val response = state.data
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState("contactUs")
                navController.navigate(Routes.HOME_SCREEN) {
                    popUpTo(0) { inclusive = true }
                }
            }

            is UiState.Error -> {
                errorMessage = state.message
                showDialog = true
                viewModel.resetState("contactUs")
            }

            is UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                viewModel.resetState("contactUs")
            }

            else -> Unit
        }
    }


    if ( state is UiState.Loading) {
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
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFFFFF),
                        Color(0xFF5C2C4D),
                        Color(0xFF5C2C4D)
                    )
                )
            )
    ) {
        // Top heading (optional back navigation)
        TopHeadingText(text = "Contact Us", onBackPress = { navController.popBackStack() }, modifier = Modifier)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                TextSection(
                    title = "For any queries or assistance, reach out to us.",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 18,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(5.dp))
                ContactUsField(value = name, onValueChange = { name = it }, hint = "Name")
                Spacer(Modifier.height(5.dp))
                ContactUsField(value = email, onValueChange = { email = it }, hint = "Email")
                Spacer(Modifier.height(5.dp))
                ContactUsField(value = phoneNumber, onValueChange = { phoneNumber = it }, hint = "Phone Number")
                Spacer(Modifier.height(5.dp))
                LongMessageTextBox(value = message, onValueChange = { message =it }, hint = "Message")
                Spacer(Modifier.height(20.dp))
                SubmitButtonSharp(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Submit",
                    onClick = {
                        if (name.isBlank() || email.isBlank() || phoneNumber.isBlank() || message.isBlank()) {
                            showError = true
                        } else if (!isSubmitting) {
                            showError = false
                            isSubmitting = true
                            coroutineScope.launch {
                                viewModel.contactUsApi(name, email, phoneNumber, message)
                                isSubmitting = false
                            }
                        }
                    },
                    fontSize = 18
                )

                if (showError) {
                    Text(
                        text = "Please fill out all fields.",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(Modifier.height(20.dp))
                ContactUsCard(
                    modifier = Modifier,
                    iconResId = R.drawable.call__ic,
                    title = stringResource(R.string.call_us_on),
                    subtitle = "+1 (XXX) XXX-XXXX"
                )
                Spacer(Modifier.height(8.dp))
                ContactUsCard(
                    modifier = Modifier,
                    iconResId = R.drawable.vector_mail,
                    title = stringResource(R.string.mail_us),
                    subtitle = "xyz@gmail.com"
                )

            }
        }
    }
}


@Composable
fun ContactUsField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String ,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF333333), fontWeight = FontWeight.Normal)) {
                        append(hint)
                    }
                    withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                        append("*")
                    }
                },
                color = Color(0xFF333333),
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 14.sp
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 5.dp)
            .background(
                color = Color(0xFFE6F3F2),
                shape = RoundedCornerShape(8.dp)
            )
            .clip(RoundedCornerShape(8.dp)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        textStyle = TextStyle(
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 14.sp
        ),
        singleLine = true,
        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun ContactUsCard(
    modifier: Modifier = Modifier,
    iconResId: Int,
    title: String,
    subtitle: String,
    onClick: (() -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White), // Ensures card background is white
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp, horizontal = 2.dp)
            .padding(top = 10.dp)
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "contact us card",
                modifier = Modifier.wrapContentSize()
            )

            Spacer(modifier = Modifier.width(22.dp))

            Column {
                Text(
                    text = title,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color.Black
                )

                Text(
                    text = subtitle,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontSize = 10.sp,
                    color = Color(0xFF333333)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactUsScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ContactUs(
            navController = navController,
        )
    }
}