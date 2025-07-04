package com.bussiness.composeseniorcare.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bussiness.composeseniorcare.R


@Composable
fun ErrorDialog(
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
    ) {
        Box(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.ic_cross),
                    contentDescription = "Cross",
                    Modifier.clickable {
                        onDismiss()
                    }.align(Alignment.End)
                        .padding(12.dp)
                )
                Text(
                    text = "Message", modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_error_icon),
                    contentDescription = "Error_icon",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .width(120.dp).height(70.dp)
                )
                Text(
                    text = message,
                    textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                    fontSize = 14.sp, fontFamily = FontFamily(Font(R.font.poppins)),
                )

                SharpEdgeButton(
                    buttonText = "Okay",
                    onClickButton = { onConfirm() },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 20.dp, vertical = 25.dp)
                        .fillMaxWidth()
                )


            }

        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ErrorDialogPreview() {
    ErrorDialog(
        message = "Something went wrong. Please try again.",
        onConfirm = {},
        onDismiss = {}
    )
}
