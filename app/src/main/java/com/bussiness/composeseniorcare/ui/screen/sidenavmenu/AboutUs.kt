package com.bussiness.composeseniorcare.ui.screen.sidenavmenu


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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.bussiness.composeseniorcare.navigation.Routes
import com.bussiness.composeseniorcare.ui.component.TopHeadingText
import com.bussiness.composeseniorcare.ui.screen.mainflow.ExpandableText
import com.bussiness.composeseniorcare.ui.theme.Purple
import com.bussiness.composeseniorcare.util.SessionManager

@Composable
fun AboutUs(navController: NavHostController,title: String) {

    val text23 by remember { mutableStateOf("Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.") }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

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
        Spacer(modifier = Modifier.height(20.dp))
        // Top heading (optional back navigation)
        TopHeadingText(text = "About Us", onBackPress = { navController.popBackStack() }, modifier = Modifier   )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 15.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top
            ) {
                SectionHeader("About Us")

                ExpandableText(
                    text = text23,
                    minimizedMaxLines = 10,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins)),
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )

                Spacer(Modifier.height(10.dp))

                DividerLine()

                ToggleTextSection(
                    title = "Privacy Policy",
                    content = text23,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    titleColor = Color.Black,
                    contentColor = Color.Black,
                    titleFont = FontFamily(Font(R.font.poppins)),
                    contentFont = FontFamily(Font(R.font.poppins)),
                    data = title
                )
                DividerLine()

                ToggleTextSection(
                    title = "Terms & Conditions",
                    content = text23,
                    modifier = Modifier.padding(horizontal = 20.dp),
                    titleColor = Color.Black,
                    contentColor = Color.Black,
                    titleFont = FontFamily(Font(R.font.poppins)),
                    contentFont = FontFamily(Font(R.font.poppins)),
                    data = "null"
                )
                DividerLine()

                Spacer(Modifier.height(10.dp))

                if(sessionManager.isLoggedIn()){
                    DeleteAccountButton { navController.navigate(Routes.DELETE_ACCOUNT) }
                }

            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.poppins)),
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun DividerLine() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        color = Purple,
        thickness = 1.dp
    )
}


@Composable
fun ToggleTextSection(
    title: String,
    content: String,
    modifier: Modifier = Modifier,
    titleColor: Color,
    contentColor: Color,
    titleFont: FontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
    contentFont: FontFamily = FontFamily(Font(R.font.poppins)),
    data: String
) {
    val initialVisibility = remember { data == title }
    var isVisible by remember { mutableStateOf(initialVisibility) }

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { isVisible = !isVisible }
    ) {
        Text(
            text = title,
            color = titleColor,
            fontSize = 16.sp,
            fontFamily = titleFont,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (isVisible) {
            Text(
                text = content,
                fontSize = 13.sp,
                color = contentColor,
                fontFamily = contentFont
            )
        }
    }
}


@Composable
fun DeleteAccountButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(20.dp)
            .background(Color.White)
            .border(width = 1.dp, color = Color(0xFFEAEAEA),shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Icon
        Image(
            painter = painterResource(id = R.drawable.delete_icon_vec),
            contentDescription = "Delete Icon",
            modifier = Modifier
                .wrapContentSize()
                .padding( 12.dp)
        )

        // Title and Description
        Column(
            modifier = Modifier.weight(1f).padding(10.dp)
        ) {
            Text(
                text = stringResource(R.string.request_to_delete_account),
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = Purple
            )

            Text(
                text = stringResource(R.string.request_to_closure_of_your_account),
                fontFamily = FontFamily(Font(R.font.poppins)),
                fontSize = 11.sp,
                color = Purple
            )
        }

        // Right Arrow
        Image(
            painter = painterResource(id = R.drawable.arrow_right),
            contentDescription = "Arrow Icon",
            modifier = Modifier
                .wrapContentSize()
                .padding( 12.dp)
        )
    }
}




@Preview(showBackground = true)
@Composable
fun AboutUsScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        AboutUs(
            navController = navController,
            title = ""
        )
    }
}