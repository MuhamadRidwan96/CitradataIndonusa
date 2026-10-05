package com.example.features.presentation.search.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.features.presentation.search.state.location.LocationUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityBottomSheet(
    modifier: Modifier = Modifier,
    idProvince: String?,
    selectedCityName: String,
    onCitySelect: (String, String?, String) -> Unit,
    onGetCity: (String?) -> Unit,
    state: LocationUiState
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val cities = remember(
        idProvince,
        state.citiesByProvince
    ) {
        state.citiesByProvince[idProvince].orEmpty()
    }

    val isCityLoading =
        state.loadingCityProvinceId == idProvince

    LaunchedEffect(expanded, idProvince,onGetCity) {
        if (!expanded) return@LaunchedEffect

        val provinceId = idProvince
            ?.takeIf { it.isNotBlank() }
            ?: return@LaunchedEffect

        // Hanya request kalau belum ada cache
        if (!state.citiesByProvince.containsKey(provinceId)) {
            onGetCity(provinceId)
        }
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            1.dp,
            Color.Gray.copy(alpha = 0.5f)
        ),
        tonalElevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 42.dp)
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                if (idProvince?.isNotBlank() == true) {
                    expanded = !expanded
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(
                readOnly = true,
                value = selectedCityName,
                onValueChange = {},
                textStyle = MaterialTheme.typography.bodySmall,
                placeholder = {
                    Text(
                        "Pilih Kota",
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor(
                        ExposedDropdownMenuAnchorType.PrimaryEditable
                    )
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
                ),
                enabled = idProvince?.isNotBlank() == true
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                when {
                    isCityLoading -> {
                        DropdownMenuItem(
                            enabled = false,
                            text = {
                                Text("Memuat...")
                            },
                            onClick = {}
                        )
                    }

                    cities.isEmpty() -> {
                        DropdownMenuItem(
                            text = {
                                Text("Tidak ada kota tersedia")
                            },
                            onClick = {
                                expanded = false
                            }
                        )
                    }

                    else -> {
                        cities.forEach { city ->

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = city.cityName,
                                        style = MaterialTheme
                                            .typography
                                            .bodySmall
                                    )
                                },
                                onClick = {
                                    onCitySelect(
                                        city.idCity,
                                        idProvince.orEmpty(),
                                        city.cityName
                                    )

                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}



