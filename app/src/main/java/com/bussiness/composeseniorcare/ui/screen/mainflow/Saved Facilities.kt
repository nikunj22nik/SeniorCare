package com.bussiness.composeseniorcare.ui.screen.mainflow

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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
import com.bussiness.composeseniorcare.model.SavedFacility
import com.bussiness.composeseniorcare.model.Services
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.AppLoader
import com.bussiness.composeseniorcare.ui.component.ErrorDialog
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.ui.theme.Redish
import com.bussiness.composeseniorcare.util.ErrorMessage
import com.bussiness.composeseniorcare.util.SessionManager
import com.bussiness.composeseniorcare.util.UiState
import com.bussiness.composeseniorcare.viewmodel.CommonViewModel
import com.bussiness.composeseniorcare.viewmodel.SavedFacilityViewModel

@Composable
fun SavedFacilities(
    navController: NavHostController,
    onOpenDrawer: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val viewModel : SavedFacilityViewModel = hiltViewModel()
    val toggleVieModel : CommonViewModel = hiltViewModel()
    val savedList = remember { mutableStateListOf<SavedFacility>() }

    val state = viewModel.uiState.value
    val toggleSavedFacility = toggleVieModel.getUiState("toggleSaveFacility").value

    LaunchedEffect(Unit) {
        viewModel.savedFacilityApi(sessionManager.getUserId().toString())
    }

    LaunchedEffect(state, toggleSavedFacility) {
        when  {
            state is UiState.Success -> {
                savedList.clear()
                savedList.addAll(state.data.savedFacilities)
                viewModel.resetState()
            }

            state is UiState.Error -> {
                errorMessage = state.message
                showDialog = true
                viewModel.resetState()
            }

            state is UiState.NoInternet -> {
                errorMessage = ErrorMessage.NO_INTERNET
                showDialog = true
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

    if (showDialog) {
        ErrorDialog(
            message = errorMessage,
            onConfirm = { showDialog = false },
            onDismiss = { showDialog = false }
        )
    }

    BackHandler {
        navController.navigate(Routes.HOME_SCREEN)
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
        CustomTopAppBar(showCredit = false, onMenuClick = onOpenDrawer)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                            append("Saved Featured")
                        }
                        withStyle(style = SpanStyle(color = Redish, fontWeight = FontWeight.Bold)) {
                            append(" Facilities")
                        }
                    },
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Spacer(Modifier.height(10.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(savedList) { saved ->
                        FacilityCard(
                            facility = saved.toFacility(),
                            showRating = false,
                            showBookmark = true,
                            onBookmarkClick = { toggleVieModel.toggleSaveFacilityApi(sessionManager.getUserId()
                                .toString(), saved.facility_id.toString()
                            ) },
                            onCardClick = { navController.navigate(Routes.LISTING_DETAIL) },
                            fromTextColor = Purple
                        )
                    }
                }
            }
        }
    }
}

fun SavedFacility.toFacility(): Facility {
    return Facility(
        f_id = facility_id,
        facility_name = facility_name,
        images = listOf(image), // Only one image in SavedFacility
        location = location,
        price = price,
        saved = saved.toIntOrNull() ?: 0,
        rating = null, // Not provided in SavedFacility
        services = Services(
            memory_care = 0,
            assisted_living = 0,
            independent_living = 0
        )
    )
}



@Preview(showBackground = true)
@Composable
fun SavedFacilitiesScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        SavedFacilities(
            navController = navController,
            onOpenDrawer = {}
        )
    }
}
