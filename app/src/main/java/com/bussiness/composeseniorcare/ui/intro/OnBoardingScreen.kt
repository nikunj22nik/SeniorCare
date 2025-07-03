package com.bussiness.composeseniorcare.ui.intro

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.OnboardingData
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@Composable
fun OnboardingScreen(navController: NavHostController) {
    val onboardingItems = listOf(
        OnboardingData(R.drawable.onb_img1, "Lorem Ipsum", "Lorem Ipsum is simply dummy text of the printing and typesetting industry."),
        OnboardingData(R.drawable.onb_img2, "Stay Connected", "Keep in touch with your loved ones easily."),
        OnboardingData(R.drawable.onb_img3, "Get Started", "Let's begin your journey with us.")
    )

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF9F9)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = onboardingItems.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            OnboardingCard(
                data = onboardingItems[page],
                pageIndex = page,
                totalPages = onboardingItems.size,
                pagerState = pagerState,
                onSkipClick = {
                   navController.navigate(Routes.LOGIN)
                },
                onNextClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage < onboardingItems.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            navController.navigate(Routes.LOGIN)
                        }
                    }
                }
            )
        }

    }
}

@Composable
fun OnboardingCard(
    data: OnboardingData,
    pageIndex: Int,
    totalPages: Int,
    pagerState: PagerState,
    onSkipClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F3F2))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.55f)
            ) {
                Image(
                    painter = painterResource(id = data.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = 20.dp,
                            )
                        )
                )

                IconButton(
                    onClick = onSkipClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 15.dp, top = 35.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.skip_ic),
                        contentDescription = "Skip",
                        tint = Color.Unspecified
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.45f)
                    .background(Color(0xFFE6F3F2))
                    .padding(10.dp)
            ) {
                HomeIconIndicator(
                    totalDots = totalPages,
                    selectedIndex = pageIndex
                )

                Text(
                    text = data.title.uppercase(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 25.sp,
                        color = Purple,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 20.dp)
                )

                Text(
                    text = data.description,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 16.sp,
                        color = Color(0xFF444444),
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontWeight = FontWeight.Normal
                    ),
                    modifier = Modifier.padding(start = 8.dp, end = 80.dp, top = 10.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    CustomPagerIndicator(
                        totalDots = totalPages,
                        selectedIndex = pageIndex
                    )
                }
            }
        }

        Image(
            painter = painterResource(id = R.drawable.side_curv_bar),
            contentDescription = "Side curve",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .clickable(
                    indication = null, // No ripple or visual effect
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onNextClick()
                }
        )
    }
}

@Composable
fun CustomPagerIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Purple,
    borderColor: Color = Purple,
    spacing: Dp = 6.dp,
    size: Dp = 9.dp,
    cornerRadius: Dp = 3.dp
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(
                        color = if (index == selectedIndex) activeColor else Color.Transparent
                    )
                    .border(
                        width = 1.dp,
                        color = borderColor,
                        shape = RoundedCornerShape(cornerRadius)
                    )
            )
        }
    }
}


@Composable
fun HomeIconIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    activeIconRes: Int = R.drawable.selected_ic_home,
    inactiveIconRes: Int = R.drawable.home_onb,
    dashLength: Float = 8f,
    gapLength: Float = 4f,
    lineColor: Color = Purple,
    lineThickness: Dp = 2.dp,
    lineLength: Dp = 22.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(totalDots) { index ->
            Image(
                painter = painterResource(id = if (index == selectedIndex) activeIconRes else inactiveIconRes),
                contentDescription = "Indicator icon $index",
                modifier = Modifier.wrapContentSize()
            )

            // Draw dashed line after each icon except the last one
            if (index < totalDots - 1) {
                Canvas(
                    modifier = Modifier
                        .height(lineThickness)
                        .width(lineLength)
                        .align(Alignment.CenterVertically)
                ) {
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, 0f),
                        end = Offset(size.width, 0f),
                        strokeWidth = lineThickness.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreview() {
    val nav = rememberNavController()
    OnboardingScreen(nav)
}
