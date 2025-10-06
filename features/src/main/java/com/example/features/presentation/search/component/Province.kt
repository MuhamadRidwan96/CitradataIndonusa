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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.common.Result
import com.example.domain.response.ProvinceResponse
import com.example.features.presentation.search.viewmodel.ProvinceViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProvinceBottomSheet(
    selectedProvince: String,
    viewModel: ProvinceViewModel,
    onProvinceSelected: (String, String) -> Unit,
) {
    val provincesState by viewModel.provinceList.collectAsState()

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        viewModel.getProvinces()
    }

    var provinces = when (provincesState) {
        is Result.Success -> (provincesState as Result.Success<ProvinceResponse>).data.data
        else -> emptyList()
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f)),
        tonalElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 42.dp)
            .clickable { expanded = !expanded }
    ) {

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(
                readOnly = true,
                value = selectedProvince,
                onValueChange = {},
                textStyle = MaterialTheme.typography.bodySmall,
                placeholder = { Text("Pilih Provinsi", style = MaterialTheme.typography.bodySmall) },
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
                provinces.forEach { province ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                province.province,
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onClick = {
                            onProvinceSelected(province.idProvince, province.province)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}


