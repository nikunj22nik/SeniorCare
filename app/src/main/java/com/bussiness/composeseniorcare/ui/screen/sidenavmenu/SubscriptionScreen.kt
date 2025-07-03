package com.bussiness.composeseniorcare.ui.screen.sidenavmenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.SubscriptionPlan
import com.bussiness.composeseniorcare.ui.component.TopHeadingText

@Composable
fun SubscriptionPlan(navController: NavHostController) {
    val plans = listOf(
        SubscriptionPlan(
            "Basic Plan", "$7.00", listOf(
                "Limited Features", "Basic Support", "Trial for Premium Features", "Community Access", "No Commitment"
            ), "Active Plan"
        ),
        SubscriptionPlan(
            "Exclusive Plan", "$24.99", listOf(
                "Premium Features", "Custom Integrations", "Personalized Onboarding", "10 Provider Call Support", "10 Facility View Access"
            ), "Upgrade"
        ),
        SubscriptionPlan(
            "Platinum Plan", "$70.00", listOf(
                "Everything in Exclusive", "Unlimited Support", "Dedicated Account Manager", "Premium Access", "Monthly Health Check Reports"
            ), "Upgrade"
        )
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
        TopHeadingText(text = "Subscription Plans", onBackPress = { navController.popBackStack() }, modifier = Modifier)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(plans.size) { index ->
                    SubscriptionPlanCard(plan = plans[index])
                }
            }
        }
    }
}

@Composable
fun SubscriptionPlanCard(plan: SubscriptionPlan) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.plan_ic),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = plan.name,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    color = Color.Black,
                    fontSize = 18.sp,
                )

                Spacer(modifier = Modifier.height(0.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = plan.price,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        fontSize = 28.sp,
                        color = Color(0xFFEA5B60)
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    Text(
                        text = "/Month",
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 45.dp, start = 16.dp, end = 16.dp)
                ) {
                    plan.features.forEach {
                        TextWithImage(text = it)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    SubscriptionButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        buttonText = plan.status.toString(),
                        onClickButton = { /*TODO*/ },
                        backColor = if (plan.status == "Active Plan") Color(0xFF9D9D9D) else Color(0xFF5C2C4D)
                    )
                }
            }
        }
    }
}

@Composable
fun SubscriptionButton(modifier: Modifier = Modifier,buttonText : String, onClickButton : () -> Unit,buttonTextSize : Int = 14,backColor: Color){
    Button(
        onClick = onClickButton,
        modifier = modifier
            .height(35.dp)
            .padding(start = 9.dp, end = 9.dp),
        colors = ButtonDefaults.buttonColors(
            backColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        Text(
            text = buttonText,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = buttonTextSize.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun TextWithImage(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(bottom = 6.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.plan_tick_ic),
            contentDescription = "tick icon",
            tint = Color.Unspecified,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontFamily = FontFamily(Font(R.font.poppins)),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF4E4E4E)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SubscriptionScreenPreview() {
    MaterialTheme {
        SubscriptionPlan(navController = rememberNavController())
    }
}
