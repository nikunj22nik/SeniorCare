package com.bussiness.composeseniorcare.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.data.model.BottomNavItem
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.util.SessionManager


val bottomNavItems = listOf(
    BottomNavItem("Home", R.drawable.home_icon, Routes.HOME_SCREEN),
    BottomNavItem("Compare Facility", R.drawable.compare_facilities_ic, Routes.COMPARE_FACILITY),
    BottomNavItem("Saved Facilities", R.drawable.saved_facilites_ic, Routes.SAVED_FACILITIES),
    BottomNavItem("Profile", R.drawable.profile_ic, Routes.PROFILE_SCREEN)
)


@Composable
fun CustomBottomBar(
    navController: NavController,
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemClick: (BottomNavItem) -> Unit
) {

    Surface(
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = item.route == selectedRoute

                Column(
                    modifier = Modifier
                        .weight(1f) // Evenly distribute based on screen width
                        .clickable { onItemClick(item) }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                if (selected) Color(0xFF5C2C4D) else Color.Transparent
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.label,
                            tint = if (selected) Color.White else Color(0xFF303030),
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.label,
                        color = if (selected) Color(0xFF5C2C4D) else Color(0xFF303030),
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        maxLines = 1
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun CustomBottomBarPreview() {
    val navController = rememberNavController()
    val currentRoute = Routes.HOME_SCREEN


    CustomBottomBar(
        navController = navController,
        items = bottomNavItems,
        selectedRoute = currentRoute,
        onItemClick = {}
    )
}