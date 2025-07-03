package com.bussiness.composeseniorcare.ui.screen.mainflow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.Facility
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.theme.Purple

@Composable
fun SavedFacilities(
    navController: NavHostController,
    facilities: List<Facility>,
    onOpenDrawer: () -> Unit
) {
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
                    text = stringResource(id = R.string.saved_featured_facilities),
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
                    items(facilities) { facility ->
                        FacilityCard(
                            facility = facility,
                            showRating = false,
                            showBookmark = true,
                            onBookmarkClick = { /* handle bookmark click */ },
                            onCardClick = { navController.navigate(Routes.LISTING_DETAIL) },
                            fromTextColor = Purple
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SavedFacilitiesScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        SavedFacilities(
            navController = navController,
            facilities = facilitiesList, // replace with preview list
            onOpenDrawer = {}
        )
    }
}
