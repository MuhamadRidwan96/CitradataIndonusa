package com.example.features.presentation.search.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.common.Result
import com.example.domain.response.RegenciesResponse
import com.example.features.presentation.search.viewmodel.CityViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityBottomSheet(
    idProvince: String?,
    selectedCityName: String,
    cityList: Result<RegenciesResponse>,
    onCitySelected: (String, String?, String) -> Unit,
    viewModel: CityViewModel,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(idProvince) {

        if (!idProvince.isNullOrEmpty()) {
            delay(1000)
            viewModel.getCity(idProvince)
        }
    }

    val cities = when (cityList) {
        is Result.Success -> cityList.data.data
        is Result.Loading -> null
        is Result.Error -> emptyList()
    }


    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
        tonalElevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 42.dp)
            .clickable { expanded = !expanded }
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                readOnly = true,
                value = selectedCityName,
                onValueChange = {},
                textStyle = MaterialTheme.typography.bodySmall,
                placeholder = { Text("Pilih Kota", style = MaterialTheme.typography.bodySmall) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                    .fillMaxWidth()
                    .height(45.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                )

            )


            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                when {
                    cities == null -> DropdownMenuItem(
                        enabled = false,
                        text = { Text("Memuat...") },
                        onClick = {}
                    )

                    cities.isEmpty() -> DropdownMenuItem(
                        text = { Text("Tidak ada kota tersedia") },
                        onClick = { expanded = false }
                    )

                    else -> cities.forEach { city ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    city.cityName,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            onClick = {
                                onCitySelected(city.idCity, city.idProvince, city.cityName)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}


