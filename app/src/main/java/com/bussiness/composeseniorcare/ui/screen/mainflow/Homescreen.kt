package com.bussiness.composeseniorcare.ui.screen.mainflow

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bussiness.composeseniorcare.BuildConfig
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.FacilityL
import com.bussiness.composeseniorcare.data.model.PosterItem
import com.bussiness.composeseniorcare.data.model.Provider
import com.bussiness.composeseniorcare.model.Facility
import com.bussiness.composeseniorcare.model.Services
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.SharpEdgeButton
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.ui.theme.Redish
import com.bussiness.composeseniorcare.util.SessionManager

val facilities = List(5) {
    PosterItem(R.drawable.poster,"Assisted Living", "Lorem ipsum dolor sit amet, consectetur adipiscing elit")
}

val facilitiesList = List(2) {
    FacilityL(
        imageResId = R.drawable.banner_bg, // Replace with a valid drawable
        name = "Lorem ipsum",
        location = "City, State, Country",
        services = "Assisted Living, Memory Care",
        price = "$25.9",
        rating = "4.8",
        isBookmarked = false
    )
}

private val featuredFacilityList = List(2) {
    Provider(
        "Mathew John",
        "Experienced caregiver with passion for memory care.",
        "City, State, Country",
        "Assisted Living, Memory Care",
        "www.abc.com",
        "https://via.placeholder.com/150"
    )
}

val banners = listOf(
    R.drawable.banner_bg,
    R.drawable.banner_bg,
    R.drawable.banner_bg
)



@Composable
fun HomeScreen(authNavController: NavHostController,navController: NavHostController, onOpenDrawer: () -> Unit) {

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var isLockedVisible by remember { mutableStateOf(true) }
    var backPressedOnce by remember { mutableStateOf(false) }


    BackHandler(true) {
        if (backPressedOnce) {
            (context as? Activity)?.finishAffinity()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            Handler(Looper.getMainLooper()).postDelayed({
                backPressedOnce = false
            }, 2000)
        }
    }

    Column(
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
        CustomTopAppBar(onMenuClick = onOpenDrawer)

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item { SearchBar() }
            item { BannerSection(banners = banners) }
            item { ExploreFacilitiesSection(facilities,onCardClick = { navController.navigate(Routes.LISTING_DETAIL) }) }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                                append("Featured ")
                            }
                            withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                                append("Facilities")
                            }
                        },
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (sessionManager.isLoggedIn()) {
                        Text(
                            text = "See all+",
                            textDecoration = TextDecoration.Underline,
                            fontSize = 10.sp,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            modifier = Modifier.clickable {
                                navController.navigate(Routes.FACILITY_LISTING)
                            }
                        )
                    }
                }
            }

            item {
                var facilitiesListing = remember { mutableStateListOf<Facility>() }
                FeaturedFacilityList(
                    facilities = facilitiesListing,
                    modifier = Modifier.fillMaxWidth(),
                    onCardClick = {
                        navController.navigate(Routes.LISTING_DETAIL )
                    }
                )
            }


            item {

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                            append("Featured ")
                        }
                        withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                            append("Providers")
                        }
                    },
                    fontFamily = FontFamily(Font(R.font.poppins_bold)),
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (isLockedVisible) {
                    LockedCardContent(
                        onLoginClick = {
                            authNavController.navigate(Routes.LOGIN)
                        },
                        onSubscriptionClick = {
                            // Hide the locked card and show the content
                            isLockedVisible = false
                        },
                        sessionManager = sessionManager
                    )
                } else {
                    FeaturedProvidersSection(
                        providers = featuredFacilityList,
                        modifier = Modifier.fillMaxWidth(),
                        onProfileClick = { navController.navigate(Routes.LISTING_DETAIL) }
                    )
                }
            }
        }
    }
}


@Composable
fun CustomTopAppBar(
    showCredit: Boolean = true,
    onMenuClick: () -> Unit
) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues()

    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = statusBarPadding.calculateTopPadding()+10.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 15.dp
            )
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Menu icon on left
            Icon(
                painter = painterResource(id = R.drawable.hamburger_ic),
                contentDescription = "Menu",
                tint = Color.Unspecified,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterStart)
                    .clickable { onMenuClick() }
            )

            // Logo always center
            Image(
                painter = painterResource(id = R.drawable.logo_arce),
                contentDescription = "GenAcre Logo",
                modifier = Modifier
                    .height(28.dp)
                    .align(Alignment.Center)
            )

            // Credit text on right if visible
            if (showCredit) {
                CreditsText(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}


@Composable
fun CreditsText(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    // Control visibility based on login
    val isVisible = remember { !sessionManager.isLoggedIn() }

    if (isVisible) {
        Row(
            modifier = modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = painterResource(id = R.drawable.twemoji_coin),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.wrapContentSize()
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Credits : 5",
                color = Color(0xFFEA5B60),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.jakarta_sans)),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.widthIn(max = 70.dp)
            )
        }
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(top = 15.dp)
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF3F3F3))
            .border(width = 1.dp, color = Color(0xFFD9D9D9), shape = RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            painter = painterResource(id = R.drawable.location_ic),
            contentDescription = "Location",
            tint = Color.Unspecified,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins))
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )

            if (searchText.isEmpty()) {
                Text(
                    text = "Search for location",
                    color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                .background(Color(0xFF5C2C4D))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Search",
                color = Color.White,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun BannerSection(banners: List<Int>) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { banners.size })

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        pageSpacing = 16.dp,
        flingBehavior = PagerDefaults.flingBehavior(state = pagerState)
    ) { page ->
        BannerItem(
            imageRes = banners[page],
            totalBanners = banners.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
fun BannerItem(
    imageRes: Int,
    totalBanners: Int,
    currentPage: Int
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Banner Text (centered)
        Text(
            text = "Explore trusted senior living facilities tailored to your needs. Search, compare, and connect effortlessly today!",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 25.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
        )

        // Indicators at the bottom center inside the banner
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(totalBanners) { index ->
                val isSelected = currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(9.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(
                            color = if (isSelected) Color.White else Color.Transparent
                        )
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(3.dp)
                        )
                )
            }
        }
    }
}



@Composable
fun ExploreFacilitiesSection(facilities: List<PosterItem>,onCardClick: () -> Unit) {
    Column(modifier = Modifier) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                    append("Explore ")
                }
                withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                    append("Facilities")
                }
            },
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow {
            items(facilities) { facility ->
                Column(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .width(145.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .clickable { onCardClick() }
                ) {
                    Image(
                        painter = painterResource(id = facility.imageResId),
                        contentDescription = facility.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(145.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Column(modifier = Modifier.padding(top = 2.dp)) {
                        Text(
                            text = facility.title,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            color = Purple,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = facility.description,
                            fontWeight = FontWeight.Normal,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            color = Color(0xFF535353),
                            fontSize = 12.sp,
                            maxLines = 2,
                            lineHeight = 15.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FacilityCard(
    facility: Facility,
    modifier: Modifier = Modifier,
    showRating: Boolean = true,
    showBookmark: Boolean = true,
    onBookmarkClick: ((Facility) -> Unit)? = null,
    onCardClick: () -> Unit,
    cornerRadius: Dp = 12.dp,
    cardElevation: Dp = 4.dp,
    fromTextColor: Color,
    arrowColor: Color = Purple
) {
    var isBookmarked by rememberSaveable { mutableStateOf(facility.saved == 1) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCardClick() }
    ) {
        Card(
            shape = RoundedCornerShape(cornerRadius),
            elevation = CardDefaults.cardElevation(defaultElevation = cardElevation),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)) {

                AsyncImage(
                    model = facility.images.firstOrNull(),
                    contentDescription = "Facility image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(R.drawable.banner_bg),
                    error = painterResource(R.drawable.banner_bg)
                )

                // Rating badge
                if (showRating) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(5.dp),
                        shadowElevation = 2.dp,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopStart)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "facility.rating",
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.star1),
                                contentDescription = "star icon",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }

                // Bookmark icon
                if (showBookmark) {
                    Icon(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.select_bm else R.drawable.bookmark_
                        ),
                        contentDescription = "Bookmark",
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                            .clickable {
                                isBookmarked = !isBookmarked
                                onBookmarkClick?.invoke(facility)
                            },
                        tint = Color.Unspecified
                    )
                }


            }
        }

        Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)) {
            FacilityTextHeading(facility.facility_name)
            FacilityTitleText("Location : ", facility.location)
            FacilityTitleText("Services : ", facility.services.getActiveServices())


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Text(
                        text = "From : ",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        color = fromTextColor
                    )
                    Text(
                        text = facility.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        color = fromTextColor
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.arrow_ic),
                    contentDescription = "arrow icon",
                    tint = arrowColor
                )
            }
        }
    }
}


@Composable
fun FeaturedProvidersSection(
    providers: List<Provider>,
    onProfileClick: (Provider) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            providers.forEach { provider ->
                ProviderCard(
                    provider = provider,
                    onProfileClick = { onProfileClick(provider) },
                    onCallClick = { /* Handle call click */ }
                )
            }
        }
    }
}


@Composable
fun ProviderCard(
    provider: Provider,
    onProfileClick: () -> Unit,
    onCallClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp) // Fixed height for both image and content
            .padding(horizontal = 0.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        AsyncImage(
            model = provider.imageUrl,
            error = painterResource(id = R.drawable.image_ic_fet),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp) // Fixed size
                .clip(RoundedCornerShape(5.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), // Match height of the row/image
            verticalArrangement = Arrangement.SpaceBetween // Even spacing inside
        ) {
            Text(
                text = provider.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                color = Purple,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = provider.description,
                fontSize = 9.sp,
                fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                color = Color(0xFF535353),
                maxLines = 2,
                lineHeight = 11.sp,
                overflow = TextOverflow.Ellipsis
            )

            InfoRow(label = "Location:", value = provider.location)
            InfoRow(label = "Services:", value = provider.services)
            InfoRow(label = "Website:", value = provider.website)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Purple)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "View Profile",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                )
            }
        }

        IconButton(
            onClick = onCallClick,
            modifier = Modifier
                .size(28.dp)
                .padding(start = 4.dp)
                .align(Alignment.Top)
        ) {
            Image(
                painter = painterResource(id = R.drawable.phone_ic),
                contentDescription = "Call"
            )
        }
    }
}

fun Services.getActiveServices(): String {
    val activeServices = mutableListOf<String>()

    if (memory_care == 1) activeServices.add("Memory Care")
    if (assisted_living == 1) activeServices.add("Assisted Living")
    if (independent_living == 1) activeServices.add("Independent Living")

    return activeServices.joinToString(", ")
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold,
            color = Purple,
            lineHeight = 15.sp,
            maxLines = 1,
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = value,
            fontSize = 9.sp,
            lineHeight = 15.sp,
            color = Color(0xFF535353),
            fontFamily = FontFamily(Font(R.font.poppins)),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LockedCardContent(
    onLoginClick: () -> Unit,
    onSubscriptionClick: () -> Unit,
    sessionManager: SessionManager
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = R.drawable.blur_ic),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )

            // Foreground content
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable._lock_ic_suns),
                    contentDescription = "Locked Icon",
                    modifier = Modifier.wrapContentSize()
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(
                        id = if (sessionManager.isSkippedLogin())
                            R.string.please_login_to_access_the_provider
                        else
                            R.string.please_Subscription
                    ),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.poppins))
                )


                Spacer(modifier = Modifier.height(10.dp))

                SharpEdgeButton(modifier = Modifier, buttonText = stringResource(
                    id = if (sessionManager.isSkippedLogin())
                        R.string.login_now
                    else
                        R.string.subscription
                ), onClickButton =
                    if (sessionManager.isSkippedLogin())
                        onLoginClick
                    else
                        onSubscriptionClick
                )
            }
        }
    }
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val authNavController = rememberNavController()

    MaterialTheme {
        HomeScreen(authNavController = authNavController,
            navController = navController,
            onOpenDrawer = {})
    }
}
