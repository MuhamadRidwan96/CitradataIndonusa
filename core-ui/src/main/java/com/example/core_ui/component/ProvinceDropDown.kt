package com.example.core_ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.domain.model.ProvinceModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAbleProvinceDropDown(
    provinces: List<ProvinceModel>,
    selectedProvince: ProvinceModel?,
    onSelectedProvince: (ProvinceModel?) -> Unit
) {


    var expanded by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val filteredProvince by remember(searchQuery, provinces) {
        derivedStateOf {
            if (searchQuery.isEmpty()) {
                provinces
            } else {
                provinces.filter {
                    it.name.contains(searchQuery, ignoreCase = true)
                }
            }
        }
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {

            OutlinedTextField(
                value = selectedProvince?.name ?: "",
                onValueChange = {
                    searchQuery = it
                    expanded = true
                },
                label = { Text("Pilih Provinsi") },
                modifier = Modifier
                    .menuAnchor()
                    .heightIn(52.dp)
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.background,
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    disabledBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f),

                ),
                singleLine = true,
                placeholder = {
                    Text("Cari Provinsi")
                }, shape = RoundedCornerShape(12.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                    focusManager.clearFocus()
                }
            ) {
                if (filteredProvince.isEmpty()) {
                    Text(
                        text = "Provinsi tidak ditemukan",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red
                    )
                } else {

                    DropdownMenuItem(
                        text = { Text("Kosongkan Pilihan") },
                        onClick = {
                            onSelectedProvince(null)
                            expanded = false
                            searchQuery = ""
                            focusManager.clearFocus()
                        }
                    )
                }
                filteredProvince.forEach { province ->
                    DropdownMenuItem(
                        text = { Text(province.name) },
                        onClick = {
                            onSelectedProvince(province)
                            searchQuery = province.name
                            expanded = false
                            focusManager.clearFocus()
                        }
                    )
                }
            }
        }
    }
}
