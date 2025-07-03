package com.bussiness.composeseniorcare.ui.screen.sidenavmenu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.FAQItem
import com.bussiness.composeseniorcare.ui.component.TopHeadingText
import com.bussiness.composeseniorcare.ui.screen.mainflow.TextSection

private val Poppins = FontFamily(Font(R.font.poppins))

@Composable
fun FAQScreen(navController: NavHostController) {
    val faqList = listOf(
        FAQItem(1, "How do I know if my loved one needs home care services?",
            "You can start by scheduling a free consultation. We will assess your loved one's needs and recommend a customized care plan."),
        FAQItem(2, "What services do you provide?",
            "We offer personal care, medication management, companionship, and more customized to your needs."),
        FAQItem(3, "Is your staff trained?",
            "Yes, all our caregivers are trained, background-checked, and insured professionals."),
        FAQItem(4, "How do you customize the care plan?",
            "We tailor it based on individual needs assessed during the initial consultation."),
        FAQItem(5, "Can I change the schedule later?",
            "Absolutely. Our care plans are flexible and adjustable based on your requirements.")
    )

    val expandedItemId = remember { mutableStateOf<Int?>(null) }

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
                verticalArrangement = Arrangement.Top,
                content = {
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
                        key = { it.id }
                    ) { faq ->
                        FAQItemView(
                            question = faq.question,
                            answer = faq.answer,
                            isExpanded = expandedItemId.value == faq.id,
                            onToggle = {
                                expandedItemId.value = if (expandedItemId.value == faq.id) null else faq.id
                            }
                        )
                    }
                }
            )
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
