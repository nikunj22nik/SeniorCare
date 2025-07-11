package com.bussiness.composeseniorcare.ui.screen.mainflow

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.model.Facility
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.AppLoader
import com.bussiness.composeseniorcare.ui.component.ErrorDialog
import com.bussiness.composeseniorcare.ui.component.FilterDialog
import com.bussiness.composeseniorcare.ui.component.SharpEdgeButton
import com.bussiness.composeseniorcare.ui.theme.Redish
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.SessionManager
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.CommonViewModel
import com.bussiness.composeseniorcare.viewmodel.FacilityListViewModel
import kotlinx.coroutines.launch


@Composable
fun FacilityListing(navController: NavHostController, onOpenDrawer: () -> Unit,type : String) {

    var showDialog by remember { mutableStateOf(false) }
    var priceRange by remember { mutableStateOf(5000f..100000f) }
    val amenities = listOf("WiFi", "Parking", "Gym", "Pool", "Spa", "Laundry")
    var selectedAmenities by remember { mutableStateOf(setOf<String>()) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    val facilitiesListing = remember { mutableStateListOf<Facility>() }
    val coroutineScope  = rememberCoroutineScope()
    //initialize viewmodel
    val viewModel : FacilityListViewModel = hiltViewModel()
    val toggleVieModel : CommonViewModel = hiltViewModel()

    //viewmodel data
    val state = viewModel.uiState.value
    val toggleSavedFacility = toggleVieModel.getUiState("toggleSaveFacility").value

    //api hit and data update
    LaunchedEffect (Unit){
        viewModel.facilityListApi(sessionManager.getUserId().toString())
    }
    //state manage
    LaunchedEffect(state,toggleSavedFacility) {
        when  {
            state is UiState.Success -> {
                facilitiesListing.clear()
                facilitiesListing.addAll(state.data.data.facility_list)
                viewModel.resetState()
            }

            state is UiState.Error -> {
                errorMessage = state.message
                showErrorDialog = true
                viewModel.resetState()
            }

            state is UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showErrorDialog = true
                viewModel.resetState()
            }

            toggleSavedFacility is UiState.Success -> {
                Toast.makeText(context, "Saved Facility Updated", Toast.LENGTH_SHORT).show()
                toggleVieModel.resetState("toggleSaveFacility")
            }

            toggleSavedFacility is UiState.Error -> {
                errorMessage = toggleSavedFacility.message
                showDialog = true
                toggleVieModel.resetState("toggleSaveFacility")
            }

            toggleSavedFacility is UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
                toggleVieModel.resetState("toggleSaveFacility")
            }

            else -> Unit
        }
    }

    if (state is UiState.Loading || toggleSavedFacility is UiState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .zIndex(1f), // to bring it on top if necessary
            contentAlignment = Alignment.Center
        ) {
            AppLoader()
        }
    }

    if (showErrorDialog) {
        ErrorDialog(
            message = errorMessage,
            onConfirm = { showErrorDialog = false },
            onDismiss = { showErrorDialog = false }
        )
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
        CustomTopAppBar(showCredit = false,onOpenDrawer)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    SearchBarWithFilter(onClickFilter = { showDialog = true }, onClickSearch = { })
                    if (showDialog) {
                        FilterDialog(
                            onDismiss = { showDialog = false },
                            onSubmit = {
                                // Do your API/filter logic here
                                showDialog = false
                            },
                            minPrice = 5000f,
                            maxPrice = 100000f,
                            priceRange = priceRange,
                            onPriceChange = { priceRange = it },
                            amenities = amenities,
                            selectedAmenities = selectedAmenities,
                            onAmenityToggle = {
                                selectedAmenities = if (selectedAmenities.contains(it)) {
                                    selectedAmenities - it
                                } else {
                                    selectedAmenities + it
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                    MapBox()
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        "Facilities",
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.poppins)),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEA5B60)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                }

                items(facilitiesListing) { facility ->
                    FacilityCard(
                        facility = facility,
                        showRating = false,
                        showBookmark = true,
                        onBookmarkClick = {
                            coroutineScope.launch {
                                toggleVieModel.toggleSaveFacilityApi(sessionManager.getUserId()
                                    .toString(), facility.f_id.toString()
                                )
                            } },
                        onCardClick = {
                            if (type == "compare"){
                                navController.navigate(Routes.COMPARE_FACILITY)
                            }else{
                                navController.navigate(Routes.LISTING_DETAIL)
                            }
                        },
                        fromTextColor = Color(0xFFEA5B60),
                        arrowColor = Redish
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(5.dp))

                        Text(text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                                append("Exclusive ")
                            }
                            withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                                append("Offers")
                            }
                        },
                            fontSize = 24.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEA5B60),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            "Lorem ipsum dolor sit amet consectetur. Orci malesuada mietmi pellentesque tincidunt at mollis facilisis. Nisl eu blandit nunc parturient adipiscing commodo.",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF535353),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        SharpEdgeButton(modifier = Modifier,"Join Now !", onClickButton = { navController.navigate(Routes.SUBSCRIPTIONS) }, buttonTextSize = 14)

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }
        }
    }
}


@Composable
fun SearchBarWithFilter(
    onClickFilter: () -> Unit,
    onClickSearch: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFF3F3F3))
            .border(1.dp, Color(0xFFD9D9D9), RoundedCornerShape(10.dp)),
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

        Text(
            text = "Address, City, ZIP",
            color = Color(0xFF333333),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.poppins)),
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(id = R.drawable.filter_ic),
            contentDescription = "Filter",
            tint = Color.Unspecified,
            modifier = Modifier
                .wrapContentSize()
                .clickable { onClickFilter() }
                .padding(end = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .clip(RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp))
                .background(Color(0xFF5C2C4D))
                .clickable { onClickSearch() }
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
fun MapBox(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
    ){
        Image(
            painter = painterResource(R.drawable.map_ic),
            contentDescription = "Map",
            modifier = Modifier.height(240.dp).fillMaxWidth(),
            contentScale = ContentScale.Fit
        )

    }
}

@Preview(showBackground = true)
@Composable
fun FacilityListingPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        FacilityListing(
            navController = navController,
            onOpenDrawer = {},
            type = ""
        )
    }
}
