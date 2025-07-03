package com.bussiness.composeseniorcare.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.ui.theme.Poppins
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.ui.theme.Redish
import com.bussiness.composeseniorcare.util.ErrorMessage


@Composable
fun EmailOrPhoneInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = ErrorMessage.EMAIL_OR_PHONE
) {
    var isError by remember { mutableStateOf(false) }

    fun validate(input: String): Boolean {
        val isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
        val isPhone = android.util.Patterns.PHONE.matcher(input).matches()
        return isEmail || isPhone
    }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color(0xFFE8F4F4), shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else Color(0xFFD0D0D0),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.mail_icon),
                    contentDescription = null,
                    tint = Color(0xFF9E9E9E),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = value,
                    onValueChange = {
                        onValueChange(it)
                        isError = !validate(it) && it.isNotEmpty()
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    textStyle = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF9E9E9E),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Inline error message
        if (isError) {
            Text(
                text = ErrorMessage.INLINE_ERROR_EMAIL,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}





@Composable
fun HeadingText(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
    )
}

@Composable
fun PasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Your Password"
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val hasMinLength = password.length >= 8
    val hasUpperCase = password.any { it.isUpperCase() }
    val hasSpecialChar = password.any { !it.isLetterOrDigit() }
    val hasNumber = password.any { it.isDigit() }

    val isValid = hasMinLength && hasUpperCase && hasSpecialChar && hasNumber
    val isError = password.isNotEmpty() && !isValid

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(color = Color(0xFFE8F4F4), shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    color = if (isError) MaterialTheme.colorScheme.error else Color(0xFFD0D0D0),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = null,
                    tint = Color(0xFF9E9E9E),
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        textStyle = LocalTextStyle.current.copy(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        decorationBox = { innerTextField ->
                            if (password.isEmpty()) {
                                Text(
                                    text = placeholder,
                                    color = Color(0xFF9E9E9E),
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.eye_ic else R.drawable.close_eye
                        ),
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = Color.Black
                    )

                }
            }
        }

        if (isError) {
            Column(modifier = Modifier.padding(top = 4.dp)) {
                if (!hasMinLength) Text("At least 8 characters", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                if (!hasUpperCase) Text("At least one uppercase letter", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                if (!hasNumber) Text("At least one number", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                if (!hasSpecialChar) Text("At least one special character", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}


@Composable
fun SubmitButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Purple,
    textColor: Color = Color.White,
    fontSize: Int = 16
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
        )
    }
}

@Composable
fun SubmitButtonSharp(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Purple,
    textColor: Color = Color.White,
    fontSize: Int = 16
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(45.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
        )
    }
}

@Composable
fun GoogleButtonWithIcon(
    text: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Purple,
    borderColor: Color = Purple
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(50.dp).background(color = Color(0xFFE6F3F2)),
        border = BorderStroke(1.dp, borderColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified // for keeping original icon color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = Poppins
        )
    }
}


@Composable
fun OtpInputField(
    otpText: String,
    onOtpTextChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    // Main clickable row that requests focus when clicked
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                focusRequester.requestFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(6) { index ->
                val char = otpText.getOrNull(index)?.toString() ?: ""
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(45.dp)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFD0D0D0),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .background(Color(0xFFE6F3F2), shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Hidden BasicTextField to actually handle the input
        BasicTextField(
            value = otpText,
            onValueChange = {
                if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                    onOtpTextChange(it)
                }
            },
            modifier = Modifier
                .focusRequester(focusRequester)
                .alpha(0f)
                .fillMaxWidth()
                .height(1.dp)
                .focusable()
        )
    }
}


@Composable
fun CompareFacilityCard(
    imageRes: Int,
    buttonText: String = "Select Facility",
    onChangeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(end = 5.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE9E9E9)),
        border = null // No stroke
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Facility Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                contentScale = ContentScale.Crop
            )

            Button(
                onClick = onChangeClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF000000)), // or your color
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(20.dp),
                contentPadding = PaddingValues(horizontal = 5.dp, vertical = 0.dp)
            ) {
                Text(
                    text = buttonText,
                    fontSize = 9.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
fun SharpEdgeButton(modifier: Modifier = Modifier,buttonText : String, onClickButton : () -> Unit,buttonTextSize : Int = 12){
    Button(
        onClick = onClickButton,
        modifier = modifier
            .height(35.dp)
            .padding(start = 9.dp, end = 9.dp),
        colors = ButtonDefaults.buttonColors(
            Purple,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = buttonText,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = buttonTextSize.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TopHeadingText(
    text: String,
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .wrapContentHeight()
            .padding(vertical = 25.dp, horizontal = 16.dp)
    ) {
        // Back icon aligned start (left)
        Icon(
            painter = painterResource(id = R.drawable.back_ic),
            contentDescription = "Back icon",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable { onBackPress() }
                .wrapContentSize()
        )

        // Title text centered absolutely
        Text(
            text = text,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.7f) // limit width to avoid overflow
        )

        // Invisible box to balance the back icon on the right, same size as icon
        Spacer(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
        )
    }
}



@Composable
fun LongMessageTextBox(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
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
            .height(100.dp) // Increase height for message box
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
        shape = RoundedCornerShape(8.dp),
        singleLine = false,
         // Allow multiline input for feedback
    )
}

@Composable
fun SkippedFormat( onLoginClick: () -> Unit,onSignUpClick: () -> Unit ){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            shape = RoundedCornerShape(3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Log In",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold
            )
        }

        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            shape = RoundedCornerShape(3.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Sign Up",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
