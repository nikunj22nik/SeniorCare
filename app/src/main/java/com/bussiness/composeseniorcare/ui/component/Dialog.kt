package com.bussiness.composeseniorcare.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.RangeSlider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bussiness.composeseniorcare.R
import com.bussiness.composeseniorcare.ui.theme.Purple

@Composable
fun LoginSuccessDialog(
    onDismiss: () -> Unit,
    onOkayClick: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                tonalElevation = 8.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    // Close icon
                    Image(
                        painter = painterResource(id = R.drawable.cross_ic),
                        contentDescription = "Close",
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.End)
                            .clickable { onDismiss() }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Success Icon Circle
                    Image(
                        painter = painterResource(id = R.drawable.tick_ic),
                        contentDescription = "Success Icon",

                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Login successfully",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onOkayClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Purple),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp).padding(bottom = 20.dp)
                    ) {
                        Text("Okay", color = Color.White, fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)))
                    }
                }
            }
        }
    }
}

@Composable
fun StatusDialog(
    onDismiss: () -> Unit,
    onYesClick: () -> Unit,
    onNoClick: () -> Unit,
    description: String,
    content: String,
    icon: Int,
    lButtonText: String,
    rButtonText: String
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color.White,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 400.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding( 8.dp)
                ) {
                    // Close icon
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            painter = painterResource(id = R.drawable.cross_ic),
                            contentDescription = "Close",
                            modifier = Modifier
                                .wrapContentSize()
                                .align(Alignment.TopEnd)
                                .clickable(onClick = onDismiss),
                            tint = Color.Unspecified
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Icon in the center
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Title
                    Text(
                        text = description,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Content
                    Text(
                        text = content,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.open_sans)),
                        color = Color(0xFF4A4A4A),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp).padding(bottom = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = onYesClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Purple,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = lButtonText,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        OutlinedButton(
                            onClick = onNoClick,
                            modifier = Modifier
                                .weight(1f)
                                .height(45.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Purple),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Purple
                            )
                        ) {
                            Text(
                                text = rButtonText,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
    minPrice: Float = 5000f,
    maxPrice: Float = 100000f,
    priceRange: ClosedFloatingPointRange<Float>,
    onPriceChange: (ClosedFloatingPointRange<Float>) -> Unit,
    amenities: List<String>,
    selectedAmenities: Set<String>,
    onAmenityToggle: (String) -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // Close Icon
                Icon(
                    painter = painterResource(id = R.drawable.cross_ic),
                    contentDescription = "Close",
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.End)
                        .clickable { onDismiss() },
                    tint = Color.Unspecified
                )

                // Title
                Text(
                    text = stringResource(id = R.string.filter),
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Filter Dropdown Row
                TopHeaderDropDown()
                Spacer(Modifier.height(25.dp))
                // Price Range
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.price_range_from_5000_to_100000),
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "$${priceRange.start.toInt()}",
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontSize = 14.sp
                        )
                        Text(
                            text = stringResource(R.string.to),
                            modifier = Modifier.padding(horizontal = 4.dp),
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "$${priceRange.endInclusive.toInt()}",
                            fontFamily = FontFamily(Font(R.font.poppins_bold)),
                            fontSize = 14.sp
                        )
                    }

                    // Rounded RangeSlider
                    RangeSlider(
                        value = priceRange,
                        onValueChange = onPriceChange,
                        valueRange = minPrice..maxPrice,
                        steps = 0,
                        modifier = Modifier.padding(top = 8.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = Purple,
                            activeTrackColor = Purple,
                            inactiveTrackColor = Color.LightGray
                        )
                    )


                }

                FilterSecondItem()

                Text(
                    text = stringResource(R.string.amenities),
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 18.dp, bottom = 0.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(amenities) { amenity ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onAmenityToggle(amenity) }
                                .padding(horizontal = 6.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = selectedAmenities.contains(amenity),
                                onCheckedChange = null, // Prevent double trigger
                                colors = CheckboxDefaults.colors(checkedColor = Purple)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = amenity,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(Font(R.font.poppins)),
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(Modifier.height(15.dp))

                SubmitButton(text = "Submit", onClick = onSubmit, modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 20)


            }

            }
        }
    }

@Composable
fun FilterSecondItem() {
    var selectedCity by remember { mutableStateOf("Any") }
    var selectedRoom by remember { mutableStateOf("Any") }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        FilterDropdown(
            label = "City",
            options = listOf("Any", "City 1", "City 2", "City 3"),
            selectedOption = selectedCity,
            onOptionSelected = { selectedCity = it },
            modifier = Modifier.weight(1f)
        )

        FilterDropdown(
            label = "Rooms",
            options = listOf("Any", "1 Room", "2 Rooms", "3+ Rooms"),
            selectedOption = selectedRoom,
            onOptionSelected = { selectedRoom = it },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun TopHeaderDropDown() {
    var selectedStatus by remember { mutableStateOf("Any") }
    var selectedType by remember { mutableStateOf("Any") }
    var selectedFacility by remember { mutableStateOf("Any") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            FilterDropdown(
                label = "Status",
                options = listOf("Any", "Healthcare", "Rehabilitation", "Pet-friendly"),
                selectedOption = selectedStatus,
                onOptionSelected = { selectedStatus = it },
                modifier = Modifier.weight(1f)
            )

            FilterDropdown(
                label = "Type",
                options = listOf("Any", "Apartment", "House", "Smart Home"),
                selectedOption = selectedType,
                onOptionSelected = { selectedType = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()) {
            FilterDropdown(
                label = "Facility",
                options = listOf("Any", "Assisted Living", "Independent Living", "Memory Care Home"),
                selectedOption = selectedFacility,
                onOptionSelected = { selectedFacility = it },
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.weight(1f))
        }
    }
}


@Composable
fun FilterDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(130.dp)
            .height(35.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
            .clickable { expanded = true }
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$label: ",
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                color = Color.Black
            )
            Text(
                text = selectedOption,
                fontSize = 10.sp,
                fontFamily = FontFamily(Font(R.font.poppins)),
                color = Color(0xFF999999),
                modifier = Modifier.weight(1f),
                maxLines = 1
            )

            Icon(
                painter = painterResource(id = R.drawable.downarrow),
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(12.dp)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(130.dp)
                .background(color = Color.White)
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    text = {
                        Text(
                            text = option,
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins)),
                            color = Color.Black,
                            maxLines = 1
                        )
                    }
                )
                if (index < options.size - 1) {
                    Divider(
                        color = Color.LightGray,
                        thickness = 0.8.dp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}





@Preview(showBackground = true)
@Composable
fun PreviewFilterDialog() {
    val open = remember { mutableStateOf(true) }
    val priceRange = remember { mutableStateOf(5000f..100000f) }
    val selectedAmenities = remember { mutableStateOf(setOf<String>()) }

    if (open.value) {
        StatusDialog(
            onDismiss = { open.value = false },
            onYesClick = { open.value = false },
            onNoClick = { open.value = false },
            description = "Deleting your Account?",
            content = "Are you sure you want to delete?",
            icon = R.drawable.tick_ic,
            lButtonText = "Delete",
            rButtonText = "Cancel"
        )
    }
}

private fun Set<String>.toggle(item: String): Set<String> =
    if (contains(item)) this - item else this + item


