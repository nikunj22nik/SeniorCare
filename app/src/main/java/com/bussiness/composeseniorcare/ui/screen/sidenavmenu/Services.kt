package com.bussiness.composeseniorcare.ui.screen.sidenavmenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.ServiceItem
import com.bussiness.composeseniorcare.ui.component.TopHeadingText
import com.bussiness.composeseniorcare.ui.screen.mainflow.TextSection
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.ui.theme.Redish

@Composable
fun ServicesScreen(navController: NavHostController) {
    val serviceList = listOf(
        ServiceItem(
            "Senior Living Communities",
            "Discover a variety of senior living communities that offer a comfortable and engaging lifestyle.",
            "Independent Living",
            "Assisted Living",
            "Memory Care",
            "Continuing Care Retirement Communities",
            "Find the perfect community that matches your lifestyle and needs.",
            R.drawable.service
        ),
        ServiceItem(
            "Assisted Living Facilities",
            "Personalized care and support for seniors who need help with daily activities while maintaining their independence.",
            "24/7 Care Assistance",
            "Medication Management",
            "Wellness Programs",
            "Recreational Activities",
            "Explore assisted living options that prioritize your well-being.",
            R.drawable.service
        ),
        ServiceItem(
            "Memory Care Services",
            "Specialized care for seniors living with Alzheimer's, dementia, or memory-related conditions.",
            "Secure Environment",
            "Cognitive Therapy Programs",
            "Trained Caregivers",
            "Family Support Services",
            "Find trusted memory care facilities designed for comfort and safety.",
            R.drawable.service
        )
    )

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
        TopHeadingText(text = " Services", onBackPress = { navController.popBackStack() }, modifier = Modifier)

        // Main scrollable content
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Our ")
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Redish,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("Services")
                        }
                    },
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                TextSection(
                    title = "Explore a wide range of senior care options tailored to meet your needs. Whether you're seeking independent living communities or specialized care services, our platform helps you find the best options in Florida with ease.",
                    modifier = Modifier.padding(vertical = 10.dp),
                    fontSize = 15,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
            }

            items(serviceList.size) { index ->
                ServiceItemView(serviceItem = serviceList[index])
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Composable
fun ServiceItemView(serviceItem: ServiceItem) {
    Image(
        painter = painterResource(id = serviceItem.imageResId),
        contentDescription = "Service Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    )
    Spacer(Modifier.height(8.dp))
   ColoredHeadingText(fullHeading = serviceItem.heading)
    Spacer(Modifier.height(10.dp))
    TextSection(title = serviceItem.description, fontSize = 15, fontFamily = FontFamily(Font(R.font.poppins)))
    Spacer(Modifier.height(5.dp))
    BulletText(text = serviceItem.subDescription1)
    BulletText(text = serviceItem.subDescription2)
    BulletText(text = serviceItem.subDescription3)
    BulletText(text = serviceItem.subDescription4)
    Text(
        text = serviceItem.quest,
        fontSize = 15.sp,
        fontFamily = FontFamily(Font(R.font.poppins)),
        fontWeight = FontWeight.Bold,
        color = Purple
    )
}

@Composable
fun BulletText(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .align(Alignment.CenterVertically)
                .background(Color.Black, shape = RoundedCornerShape(50))
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextSection(title = text, fontSize = 14, fontFamily = FontFamily(Font(R.font.poppins)))
    }
}

@Composable
fun ColoredHeadingText(fullHeading: String) {
    val parts = fullHeading.split(" ", limit = 2)
    val firstWord = parts.getOrNull(0) ?: ""
    val rest = parts.getOrNull(1) ?: ""

    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(firstWord)
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    color = Redish,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append(rest)
            }
        },
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.poppins)),
        fontWeight = FontWeight.Bold
    )
}


@Preview(showBackground = true)
@Composable
fun ServicesScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ServicesScreen(
            navController = navController,
        )
    }
}

