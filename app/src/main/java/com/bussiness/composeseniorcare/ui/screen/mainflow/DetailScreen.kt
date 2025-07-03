package com.bussiness.composeseniorcare.ui.screen.mainflow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.SharpEdgeButton
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.ui.theme.Redish

@Composable
fun ListingDetail(navController: NavHostController,
                  onOpenDrawer: () -> Unit) {

    val imageList = listOf(
        R.drawable.detail_map_ic,
        R.drawable.banner_bg,
        R.drawable.onb_img1,
        R.drawable.onb_img2,
        R.drawable.onb_img3,
        R.drawable.detail_map_ic,
        R.drawable.image_3,
        R.drawable.detail_map_ic
    )
    val amenitiesImages = listOf(
        R.drawable.banner_bg,
        R.drawable.img_2,
        R.drawable.detail_map_ic,
        R.drawable.onb_img1,
        R.drawable.img_2,
        R.drawable.detail_map_ic,
        R.drawable.onb_img1
    )

    var selectedImage by remember { mutableStateOf(imageList.first()) }
    var isBookmarked by remember { mutableStateOf(false) }

    Column(
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
        CustomTopAppBar(showCredit = false,onOpenDrawer)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                ImageBox(selectedImage)
                Spacer(modifier = Modifier.height(16.dp))
                ImageSlider(imageList = imageList) {
                    selectedImage = it
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( vertical = 4.dp)
                ) {
                    Text(
                        text = "Name Of Society",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                    )

                    Spacer(Modifier.weight(1f))

                    Icon(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.select_bm else R.drawable.vector_bookmark
                        ),
                        contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { isBookmarked = !isBookmarked }
                    )
                }
                Spacer(Modifier.height(2.dp))
                TextSection(title = "lorem ipsum lorem ipsumlorem\n ipsumlorem ipsum", modifier = Modifier.padding(bottom = 8.dp), fontSize = 12, fontFamily = FontFamily(Font(R.font.poppins)))
                Divider(Modifier.height(1.dp), color = Purple)
                Spacer(Modifier.height(8.dp))
                TextSection("Facility ID: W1254689", modifier = Modifier, fontSize = 16, fontFamily = FontFamily(Font(R.font.poppins_semi_bold)))
                Spacer(Modifier.height(5.dp))
                TextSection("Floor: 10th of 21 Floors", modifier = Modifier, fontSize = 12, fontFamily = FontFamily(Font(R.font.poppins)))
                Spacer(Modifier.height(5.dp))
                TextSection("Status: Immediately", modifier = Modifier, fontSize = 12, fontFamily = FontFamily(Font(R.font.poppins)))
                Spacer(Modifier.height(5.dp))
                TextSection("Furnished Status: lorem ipsum", modifier = Modifier, fontSize = 12, fontFamily = FontFamily(Font(R.font.poppins)))
                Spacer(Modifier.height(5.dp))
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        TextSection("Year Built: 2006", modifier = Modifier, fontSize = 12, fontFamily = FontFamily(Font(R.font.poppins)))
                        Spacer(Modifier.height(5.dp))
                        TextSection("Garage: 1", modifier = Modifier, fontSize = 12, fontFamily = FontFamily(Font(R.font.poppins)))
                    }
                    Spacer(Modifier.weight(1f))
                    CompareButton(onCompareClick = {navController.navigate(Routes.COMPARE_FACILITY) }, buttonText = "Compare Now", buttonTextSize = 14)
                }
                Spacer(Modifier.height(10.dp))
                ProviderInfoTag(onContactClick = { })
                Spacer(Modifier.height(10.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                            append("About ")
                        }
                        withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                            append("Me")
                        }
                    },
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF535353),
                    lineHeight = 20.sp
                )
                Spacer(Modifier.height(15.dp))
                Text(
                    text = "Overview",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = Redish
                )
                Spacer(Modifier.height(10.dp))
                ExpandableText(modifier = Modifier,"Dreamscape Residences by Urban Habitat is located in Sector 25A, Noida West, sprawling across an expansive 12-acre campus. This premium project offers thoughtfully designed 2 BHK, 2 BHK + study, 3 BHK, and 3 BHK + study apartments, all ready for immediate possession. Situated at a prime junction connecting Greater Noida and Ghaziabad, Dreamscape Residences ensures seamless connectivity to key areas and highways, making it a top choice for stress-free commuting.",
                    minimizedMaxLines = 3,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    color = Color(0xFF535353))
                Text(
                    text = "Amenities",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    color = Redish
                )
                Spacer(Modifier.height(10.dp))
                AmenitiesCarousel(imageList = amenitiesImages)
                Spacer(Modifier.height(10.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                            append("Download ")
                        }
                        withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                            append("Brochure")
                        }
                    },
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(Modifier.height(15.dp))
                SharpEdgeButton(modifier = Modifier.align(Alignment.CenterHorizontally),"Download", onClickButton = { }, buttonTextSize = 14)
                Spacer(Modifier.height(15.dp))
            }
        }
    }
}

@Composable
fun ImageBox(selectedImage : Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.LightGray)
    ) {
        Image(
            painter = painterResource(selectedImage),
            contentDescription = "Main Detail Image",
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ImageSlider(imageList: List<Int>, onImageClick: (Int) -> Unit) {

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
    ) {
        items(imageList) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Thumbnail Image",
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.LightGray)
                    .clickable { onImageClick(imageRes) },
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TextSection(title: String, modifier: Modifier = Modifier,fontSize : Int,fontFamily: FontFamily,fontWeight: FontWeight = FontWeight.Normal) {
    Text(
        text = title,
        modifier = modifier,
        fontSize = fontSize.sp,
        fontFamily = fontFamily,
        fontWeight = fontWeight,
        color = Color.Black
    )
}

@Composable
fun CompareButton(onCompareClick: () -> Unit, buttonText: String, buttonTextSize: Int,color: Color = Redish) {
    Button(
        onClick = onCompareClick,
        modifier = Modifier
            .height(38.dp),
        colors = ButtonDefaults.buttonColors(
            color,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp),
        elevation = ButtonDefaults.buttonElevation(4.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp) // Adjust as needed
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
fun ProviderInfoTag(onContactClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(38.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Purple),
        contentAlignment = Alignment.CenterStart // Align text vertically center & start
    ) {
        Text(
            text = "Provider Information",
            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
    Spacer(Modifier.height(10.dp))
    Row(
        modifier = Modifier.fillMaxWidth().height(135.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_2),
            contentDescription = "Provider Image",
            modifier = Modifier
                .height(130.dp)
                .width(180.dp)
                .padding(end = 5.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(start = 10.dp).fillMaxHeight()){
            TextSection("John Deo", modifier = Modifier.padding(start = 10.dp), fontSize = 16, fontFamily = FontFamily(Font(R.font.poppins_semi_bold)))
            TextSection("Agent of Facility", modifier = Modifier.padding(start = 10.dp), fontSize = 12, fontFamily = FontFamily(Font(R.font.poppins)))
            Spacer(Modifier.height(8.dp))
//            SharpEdgeButton(modifier = Modifier.align(Alignment.Start),"Contact Now", onContactClick, buttonTextSize = 13)
            CompareButton(onCompareClick = { }, buttonText = "Contact Now", buttonTextSize = 13, color = Purple)
        }
    }
}

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    minimizedMaxLines: Int = 3,
    fontSize: TextUnit = 14.sp,
    fontFamily: FontFamily = FontFamily.Default,
    color: Color
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isTextOverflowing by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (isTextOverflowing) isExpanded = !isExpanded
            }
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontFamily = fontFamily,
            maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
            color = color,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                isTextOverflowing = textLayoutResult.hasVisualOverflow
            }
        )

        if (isTextOverflowing) {
            Text(
                text = if (isExpanded) "Show less" else "Read more",
                color = Redish,
                fontSize = 12.sp,
                fontFamily = fontFamily,
                modifier = Modifier.padding(top = 4.dp)
                    .align(Alignment.End),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun AmenitiesCarousel(imageList: List<Int>) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(imageList) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Amenities Image",
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .height(80.dp)
                    .width(140.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ListingDetailScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ListingDetail(navController = navController, onOpenDrawer = {})
    }
}
