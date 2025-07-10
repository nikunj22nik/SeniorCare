package com.bussiness.composeseniorcare.ui.screen.mainflow

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.model.VerificationType
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.AppLoader
import com.bussiness.composeseniorcare.ui.component.ErrorDialog
import com.bussiness.composeseniorcare.ui.component.SubmitButton
import com.bussiness.composeseniorcare.ui.component.TopHeadingText
import com.bussiness.composeseniorcare.ui.component.VerifyOTPDialog
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.SessionManager
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.ProfileViewModel
import com.bussiness.composeseniorcare.viewmodel.VerifyOTPViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Composable
fun ProfileScreen(navController: NavHostController) {

    var clickedEdit by remember { mutableStateOf(false) }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var showImagePickerDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val viewModel: ProfileViewModel = hiltViewModel()
    val otpViewModel : VerifyOTPViewModel = hiltViewModel()

    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var profileImageUrl by remember { mutableStateOf<String?>(null) }
    var showVerifyDialog by remember { mutableStateOf(false) }
    var otp by remember { mutableStateOf("") }
    var verifyingField by remember { mutableStateOf<VerificationType?>(null) }
    val coroutineScope = rememberCoroutineScope()


    // API states
    val getProfileState = viewModel.getUiState("getProfile").value
    val editProfileState = viewModel.getUiState("editProfile").value
    val sendPhoneOtpState = otpViewModel.getUiState("sendPhoneOtp").value
    val verifyPhoneOtpState = otpViewModel.getUiState("verifyPhoneOtp").value
    val sendEmailOtpState = otpViewModel.getUiState("sendEmailOtp").value
    val verifyEmailOtpState = otpViewModel.getUiState("verifyEmailOtp").value

    // Initial Profile Load
    LaunchedEffect(Unit) {
        viewModel.getProfile(sessionManager.getUserId().toString())
    }

    //  Handle Profile Get Response
    LaunchedEffect(getProfileState, editProfileState, sendPhoneOtpState,verifyPhoneOtpState,sendEmailOtpState,verifyEmailOtpState) {
        when {
            getProfileState is UiState.Success -> {
                val profile = getProfileState.data.data
                if (profile != null) {
                    name = profile.name.toString()
                }
                if (profile != null) {
                    email = profile.email.toString()
                }
                if (profile != null) {
                    phone = profile.phone.toString()
                }
                if (profile != null) {
                    location = profile.location.toString()
                }
                if (profile != null) {
                    profileImageUrl = profile.profileImage
                }
                viewModel.resetState("getProfile")
            }

            getProfileState is UiState.Error -> {
                errorMessage = getProfileState.message
                showDialog = true
                viewModel.resetState("getProfile")
            }

            getProfileState == UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                viewModel.resetState("getProfile")
            }

            editProfileState is UiState.Success -> {
                Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                viewModel.getProfile(sessionManager.getUserId().toString()) // Refresh profile
                viewModel.resetState("editProfile")
            }

            editProfileState is UiState.Error -> {
                errorMessage = editProfileState.message
                showDialog = true
                viewModel.resetState("editProfile")
            }

            editProfileState == UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                viewModel.resetState("editProfile")
            }

            sendPhoneOtpState is UiState.Success -> {
                Toast.makeText(context, "OTP sent", Toast.LENGTH_SHORT).show()
                otpViewModel.resetState("sendPhoneOtp")
            }

            sendPhoneOtpState is UiState.Error -> {
                errorMessage = sendPhoneOtpState.message
                showDialog = true
                otpViewModel.resetState("sendPhoneOtp")
            }

            sendPhoneOtpState == UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                otpViewModel.resetState("sendPhoneOtp")
            }

            verifyPhoneOtpState is UiState.Success -> {
                Toast.makeText(context, "OTP verified", Toast.LENGTH_SHORT).show()
                showVerifyDialog = false
                otp = ""
                otpViewModel.resetState("verifyPhoneOtp")
            }

            verifyPhoneOtpState is UiState.Error -> {
                errorMessage = verifyPhoneOtpState.message
                showDialog = true
                otpViewModel.resetState("verifyPhoneOtp")
            }

            verifyPhoneOtpState == UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                otpViewModel.resetState("verifyPhoneOtp")
            }

            sendEmailOtpState is UiState.Success -> {
                Toast.makeText(context, "OTP sent to your mail.", Toast.LENGTH_SHORT).show()
                otpViewModel.resetState("sendEmailOtp")
            }

            sendEmailOtpState is UiState.Error -> {
                errorMessage = sendEmailOtpState.message
                showDialog = true
                otpViewModel.resetState("sendEmailOtp")
            }

            sendEmailOtpState is UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                otpViewModel.resetState("sendEmailOtp")
            }

            verifyEmailOtpState is UiState.Success -> {
                Toast.makeText(context, "OTP verified", Toast.LENGTH_SHORT).show()
                showVerifyDialog = false
                otp = ""
                otpViewModel.resetState("verifyEmailOtp")
            }

            verifyEmailOtpState is UiState.Error -> {
                errorMessage = verifyEmailOtpState.message
                showDialog = true
                otpViewModel.resetState("verifyEmailOtp")
            }

            verifyEmailOtpState is UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                otpViewModel.resetState("verifyEmailOtp")
            }

        }
    }

    // Show loader if any operation is in progress
    if (getProfileState is UiState.Loading || editProfileState is UiState.Loading || sendPhoneOtpState is UiState.Loading
        || verifyPhoneOtpState is UiState.Loading || sendEmailOtpState is UiState.Loading || verifyEmailOtpState is UiState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.3f)).zIndex(1f), contentAlignment = Alignment.Center
        ) {
            AppLoader()
        }
    }

    if (showDialog) {
        ErrorDialog(message = errorMessage, onConfirm = { showDialog = false }, onDismiss = { showDialog = false })
    }

    val launcherGallery = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        uri: Uri? ->
        profileImageUri = uri
    }

    val launcherCamera = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
        bitmap: Bitmap? ->
        bitmap?.let {
            val uri = saveBitmapToCache(context, it)
            profileImageUri = uri
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
        isGranted ->
        if (isGranted) {
            launcherCamera.launch(null) // Launch camera if permission granted
        } else {
            Toast.makeText(context, "Camera permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    fun launchCameraWithPermissionCheck() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) -> {
                launcherCamera.launch(null)
            }
            else -> {
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(colors = listOf(Color(0xFFFFFFFF), Color(0xFF5C2C4D), Color(0xFF5C2C4D)))
            )
    ) {
        TopHeadingText(text = "Profile", onBackPress = { navController.navigate(Routes.HOME_SCREEN) }, modifier = Modifier)

        BackHandler {
            navController.navigate(Routes.HOME_SCREEN)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 15.dp)
                    .background(Color.White)
            ) {
                // Scrollable content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(top = 28.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        ProfileImageWithCamera(
                            profileImage = when {
                                profileImageUri != null -> rememberAsyncImagePainter(profileImageUri) // user picked image
                                !profileImageUrl.isNullOrEmpty() -> rememberAsyncImagePainter(profileImageUrl) // from API
                                else -> painterResource(id = R.drawable.profile_ic_image) // fallback
                            },
                            cameraIcon = painterResource(id = R.drawable.cam_ic),
                            onCameraClick = {
                                showImagePickerDialog = true
                            }
                        )
                    }

                    if (showImagePickerDialog) {
                        AlertDialog(
                            onDismissRequest = { showImagePickerDialog = false },
                            title = { Text("Select Option") },
                            buttons = {
                                Column(Modifier.padding(16.dp)) {
                                    Text("Camera", Modifier.clickable {
                                        showImagePickerDialog = false
                                        launchCameraWithPermissionCheck()
                                    }.padding(8.dp))

                                    Text("Gallery", Modifier.clickable {
                                        showImagePickerDialog = false
                                        launcherGallery.launch("image/*")
                                    }.padding(8.dp))
                                }
                            }
                        )
                    }

                    // Show "Edit Profile" only if not editing
                    if (!clickedEdit) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            EditProfileButton(
                                onClick = { clickedEdit = true }
                            )
                        }
                    }

                    ProfileInfoItem(
                        label = "Name",
                        value = name,
                        onValueChange = { name = it },
                        icon = painterResource(id = R.drawable.name_ic),
                        showEditIcon = clickedEdit,
                        isEditable = clickedEdit
                    )

                    Spacer(Modifier.height(5.dp))

                    ValidityInfoItem(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        icon = painterResource(id = R.drawable.email_ic),
                        isEditable = clickedEdit,
                        showEditIcon = clickedEdit,
                        onVerifyClick = {
                            otpViewModel.sendEmailOtp(email)
                            verifyingField = VerificationType.EMAIL
                            showVerifyDialog = true }
                    )

                    Spacer(Modifier.height(5.dp))

                    ValidityInfoItem(
                        label = "Phone",
                        value = phone,
                        onValueChange = { phone = it },
                        icon = painterResource(id = R.drawable.call_ic),
                        isEditable = clickedEdit,
                        showEditIcon = clickedEdit,
                        onVerifyClick = {
                            otpViewModel.sendPhoneOtp(phone)
                            verifyingField = VerificationType.PHONE
                            showVerifyDialog = true
                        }
                    )

                    Spacer(Modifier.height(5.dp))

                    ProfileInfoItem(
                        label = "Location",
                        value = location,
                        onValueChange = { location = it },
                        icon = painterResource(id = R.drawable.loc_ic),
                        showEditIcon = clickedEdit,
                        isEditable = clickedEdit
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Show Save button only when editing
                if (clickedEdit) {
                    SubmitButton(
                        text = "Save Changes",
                        onClick = {
                            val imagePart = uriToMultipart(context, profileImageUri)
                            viewModel.editProfile(
                                id = sessionManager.getUserId().toString(),
                                name = name,
                                email = email,
                                phone = phone,
                                location = location,
                                profileImage = imagePart
                            )
                            clickedEdit = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 20.dp),
                        fontSize = 20
                    )
                }

                if (showVerifyDialog) {
                    val title = when (verifyingField) {
                        VerificationType.EMAIL -> "Verify Email"
                        VerificationType.PHONE -> "Verify Phone"
                        else -> ""
                    }

                    val content = when (verifyingField) {
                        VerificationType.EMAIL -> "Enter the OTP sent to your email."
                        VerificationType.PHONE -> "Enter the OTP sent to your phone."
                        else -> ""
                    }

                    VerifyOTPDialog(
                        onDismiss = {
                            showVerifyDialog = false
                            otp = ""
                        },
                        otpText = otp,
                        onOtpTextChange = { otp = it },
                        onYesClick = {
                            if (otp.isEmpty() || otp.length < 6) {
                                Toast.makeText(context, ErrorMessage.INVALID_OTP, Toast.LENGTH_SHORT).show()
                                return@VerifyOTPDialog
                            } else {
                                coroutineScope.launch {
                                    if (verifyingField == VerificationType.PHONE) {
                                        otpViewModel.verifyPhoneOtp(phone, otp)
                                    } else {
                                        otpViewModel.verifyEmailOtp(otp, email)
                                    }
                                }
                            }
                        },
                        onNoClick = {
                            showVerifyDialog = false
                            otp = ""
                        },
                        description = title,
                        content = content,
                        icon = R.drawable.tick_ic,
                        lButtonText = "Verify",
                        rButtonText = "Cancel"
                    )
                }

            }
        }
    }
}

fun uriToMultipart(context: Context, uri: Uri?, partName: String = "profileImage"): MultipartBody.Part? {
    uri ?: return null
    val contentResolver = context.contentResolver
    val fileType = contentResolver.getType(uri) ?: "image/*"
    val inputStream = contentResolver.openInputStream(uri) ?: return null
    val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
    tempFile.outputStream().use { inputStream.copyTo(it) }

    val requestFile = tempFile.asRequestBody(fileType.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
}


@Composable
fun ProfileImageWithCamera(
    profileImage: Painter,
    cameraIcon: Painter,
    onCameraClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .size(120.dp)
            .padding(8.dp),

    ) {
        // Circular Profile Image
        Image(
            painter = profileImage,
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)

        )

        // Camera Icon Overlay
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color(0xFF5C2C4D))
                .clickable { onCameraClick() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = cameraIcon,
                contentDescription = "Camera Icon",
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}

@Composable
fun EditProfileButton(
    onClick: () -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, Purple),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Purple
        ),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 3.dp),
        modifier = Modifier.height(32.dp).padding(vertical = 2.dp)
    ) {
        Text(
            text = "Edit profile",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontWeight = FontWeight.Normal,
            color = Purple
        )
    }
}

fun saveBitmapToCache(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "profile_pic_${System.currentTimeMillis()}.png")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        it.flush()
    }
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}

@Composable
fun ProfileInfoItem(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    isEditable: Boolean = true,
    showEditIcon: Boolean = false,
    onEditClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 15.dp)
    ) {
        // Icon on the left
        Card(
            modifier = Modifier
                .padding(2.dp)
                .wrapContentSize(),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = CircleShape)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "Field Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Label and text input
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = label,
                color = Color(0xFF4E4E4E),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = {
                        if (isEditable) onValueChange(it)
                    },
                    singleLine = true,
                    enabled = isEditable,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        color = if (isEditable) Color.Black else Color.Black
                    ),
                    cursorBrush = SolidColor(if (isEditable) Color.Black else Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = if (showEditIcon) 40.dp else 0.dp)
                        .align(Alignment.BottomStart),
                    decorationBox = { innerTextField ->
                        Box {
                            innerTextField()
                        }
                    }
                )

                if (showEditIcon) {
                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(36.dp)
                            .padding(end = 4.dp, top = 4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_pen),
                            contentDescription = "Edit",
                            tint = Color.Unspecified
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color(0xFFD4D4D4)
                )
            }
        }
    }
}

@Composable
fun ValidityInfoItem(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: Painter,
    modifier: Modifier = Modifier,
    isEditable: Boolean = true,
    showEditIcon: Boolean,
    onVerifyClick: () -> Unit = {}
) {
    val grayColor = Color(0xFFD4D4D4)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(top = 15.dp)
    ) {
        // Left icon
        Card(
            modifier = Modifier
                .padding(2.dp)
                .wrapContentSize(),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Box(
                modifier = Modifier
                    .background(Color.White, shape = CircleShape)
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "Field Icon",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Right side content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = label,
                color = Color(0xFF4E4E4E),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // Key to make content wrap tightly
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // TextField aligned to bottom
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        singleLine = true,
                        enabled = isEditable,
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            color = Color.Black
                        ),
                        cursorBrush = SolidColor(Color.Black),
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.Bottom)
                            .padding(end = 8.dp),
                        decorationBox = { innerTextField ->
                            Box {
                                innerTextField()
                            }
                        }
                    )

                    // Verify button
                    if (showEditIcon) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(5.dp))
                                .background(color = Purple)
                                .clickable { onVerifyClick() }
                                .padding(horizontal = 10.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "Verify",
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontSize = 10.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = grayColor
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ProfileScreen(
            navController = navController,
        )
    }
}