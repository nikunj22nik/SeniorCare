package com.bussiness.composeseniorcare.ui.screen.sidenavmenu

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.ui.component.StatusDialog
import com.bussiness.composeseniorcare.ui.component.SubmitButton
import com.bussiness.composeseniorcare.ui.component.TopHeadingText
import com.bussiness.composeseniorcare.ui.screen.mainflow.TextSection

@Composable
fun DeleteAccount(navController: NavHostController) {
    var selectedReason by rememberSaveable { mutableStateOf("") }
    var otherMessage by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val reasons = listOf(
        "I don’t want to use Home Care anymore",
        "I’m using a different account",
        "I’m worried about my privacy",
        "You are sending me too many emails/notifications",
        "This app is not working properly",
        "Other"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFF5C2C4D),
                        Color(0xFF5C2C4D)
                    )
                )
            )
    ) {
        TopHeadingText(
            text = "Delete account",
            onBackPress = { navController.popBackStack() }
        )

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
                    .verticalScroll(rememberScrollState())
            ) {

                TextSection(
                    title = "Delete my account",
                    modifier = Modifier.padding(top = 10.dp),
                    fontSize = 24,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                )
                TextSection(
                    title = "Why would you like to delete your account.",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 14,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .border(1.dp, Color(0xFFEAEAEA), RoundedCornerShape(5.dp))
                        .background(Color.White)
                ) {
                    DeleteFeedbackCard(
                        items = reasons,
                        selectedItem = selectedReason,
                        otherMessage = otherMessage,
                        onOtherMessageChange = { otherMessage = it },
                        onItemClick = { reason ->
                            selectedReason = if (selectedReason == "Other" && reason == "Other") "" else reason
                            if (reason != "Other") {
                                showDialog = true
                                otherMessage = ""
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (selectedReason == "Other") {
                    SubmitButton(
                        text = "Delete my account",
                        onClick = {
                            if (otherMessage.isNotEmpty()) {
                                showDialog = true
                            }else{
                                showDialog = false
                                Toast.makeText(context, "Please enter a message", Toast.LENGTH_SHORT).show()
                            }
                             },
                        modifier = Modifier.padding(bottom = 10.dp),
                        fontSize = 20
                    )
                }

                if (showDialog) {
                    StatusDialog(
                        onDismiss = {
                            showDialog = false
                            navController.popBackStack()
                        },
                        onYesClick = {
                            showDialog = false
                        },
                        onNoClick = {
                            showDialog = false
                        },
                        description = "Deleting your Account?",
                        content = "Are you sure you want to delete?",
                        icon = R.drawable.delete_v,
                        lButtonText = "Delete",
                        rButtonText = "Cancel"
                    )
                }
            }
        }
    }
}

@Composable
fun DeleteFeedbackCard(
    items: List<String>,
    selectedItem: String,
    otherMessage: String,
    onOtherMessageChange: (String) -> Unit,
    onItemClick: (String) -> Unit
) {
    Column {
        items.forEachIndexed { index, reason ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(reason) }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = reason,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Icon(
                        painter = painterResource(
                            id = if (selectedItem == "Other" && reason == "Other") {
                                R.drawable.arrow_down
                            } else {
                                R.drawable.arrow_right
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.Unspecified
                    )
                }

                if (selectedItem == "Other" && reason == "Other") {
                    OutlinedTextField(
                        value = otherMessage,
                        onValueChange = onOtherMessageChange,
                        placeholder = {
                            Text(
                                text = "Please share your feedback (Optional)",
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontSize = 12.sp,
                                color = Color.Black// Typically gray for placeholder
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFEAEAEA),
                            unfocusedBorderColor = Color(0xFFEAEAEA)
                        )
                    )

                }

                if (index != items.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color(0xFFEAEAEA)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        DeleteAccount(navController = navController)
    }
}
