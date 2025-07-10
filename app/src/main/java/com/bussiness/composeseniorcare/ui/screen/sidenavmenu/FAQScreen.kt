package com.bussiness.composeseniorcare.ui.screen.sidenavmenu

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.model.Data
import com.bussiness.composeseniorcare.ui.component.AppLoader
import com.bussiness.composeseniorcare.ui.component.ErrorDialog
import com.bussiness.composeseniorcare.ui.component.TopHeadingText
import com.bussiness.composeseniorcare.ui.screen.mainflow.TextSection
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.FAQViewModel

private val Poppins = FontFamily(Font(R.font.poppins))

@Composable
fun FAQScreen(navController: NavHostController) {
    val viewModel: FAQViewModel = hiltViewModel()
    val state = viewModel.uiState.value

    val expandedItemId = remember { mutableStateOf<Int?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Initially empty list, will fill from API
    var faqList by remember { mutableStateOf<List<Data>>(emptyList()) }

    // Call API on first load
    LaunchedEffect(Unit) {
        viewModel.faqApi()
    }

    // Observe state and update faqList / show dialog
    LaunchedEffect(state) {
        when (state) {
            is UiState.Success -> {
                val response = state.data
                faqList = response.data!!
                viewModel.resetState()
            }

            is UiState.Error -> {
                errorMessage = state.message
                showDialog = true
                viewModel.resetState()
            }

            is UiState.NoInternet -> {
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
                    colors = listOf(Color.White, Color(0xFF5C2C4D), Color(0xFF5C2C4D))
                )
            )
    ) {
        TopHeadingText(
            text = "FAQ",
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    TextSection(
                        title = "Frequently Asked Questions",
                        modifier = Modifier.padding(vertical = 10.dp),
                        fontSize = 22,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold
                    )
                    TextSection(
                        title = "Lorem ipsum dolor sit amet consectetur. Orci malesuada mi et mi pellentesque tincidunt at mollis facilisis. Nisl eu blandit nunc parturient adipiscing commodo.",
                        fontSize = 14,
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }

                items(
                    items = faqList,
                    key = { it.id!! }
                ) { faq ->
                    faq.question?.let {
                        faq.answer?.let { it1 ->
                            FAQItemView(
                                question = it,
                                answer = it1,
                                isExpanded = expandedItemId.value == faq.id,
                                onToggle = {
                                    expandedItemId.value = if (expandedItemId.value == faq.id) null else faq.id
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FAQItemView(
    question: String,
    answer: String,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggle() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = question,
                modifier = Modifier.weight(1f),
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 2,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                painter = painterResource(
                    id = if (isExpanded) R.drawable.arrow_down else R.drawable.arrow_right
                ),
                contentDescription = if (isExpanded) "Collapse" else "Expand",
                modifier = Modifier.size(16.dp),
                tint = Color.Black
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Text(
                text = answer,
                fontFamily = Poppins,
                fontSize = 13.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp,),
                lineHeight = 18.sp
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            color = Color(0xFFEAEAEA),
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FAQScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        FAQScreen(navController = navController)
    }
}
