package com.example.features.presentation.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.AppBottomSheet
import com.example.core_ui.component.AppOutlinedTextFieldEnableFalse
import com.example.core_ui.component.CompactSearchBar
import com.example.features.presentation.search.ProvinceEvent
import com.example.features.presentation.search.ProvinceViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProvinceBottomSheet(
    onDismiss: () -> Unit,
    viewModel: ProvinceViewModel,
    onProvinceSelected: (String) -> Unit
) {

    var showListProvince by rememberSaveable { mutableStateOf(false) }
    val query by viewModel.query
    val provinces by viewModel.provinceList.collectAsState()
    val searchState by viewModel.provinceStateViewModel.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }


    LaunchedEffect(showListProvince) {
        if (showListProvince) {
            viewModel.getProvinces()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.provinceEvent.collectLatest { event ->
            when (event) {
                is ProvinceEvent.Success -> {}
                is ProvinceEvent.Error -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Column {
        AppOutlinedTextFieldEnableFalse(
            value = searchState.provinceName,
            onClearClicked = { viewModel.updateProvince("", "") },
            placeHolder = "Pilih Provinsi",
            onClicked = { showListProvince = true }
        )

        AppBottomSheet(
            isVisible = showListProvince,
            onDismiss = {
                showListProvince = false
                onDismiss()
            },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CompactSearchBar(
                    query = query,
                    onSearchClicked = { },
                    onQueryChange = viewModel::onQueryChange
                )

                // Filtered province list
                val filteredProvinces = provinces.filter {
                    it.name!!.contains(query, ignoreCase = true)
                }

                LazyColumn {
                    items(filteredProvinces) { province ->
                        Text(
                            text = province.name!!,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateProvince(
                                        id = province.idProvince!!,
                                        name = province.name!!
                                    )
                                    onProvinceSelected(province.idProvince!!)
                                    showListProvince = false
                                    onDismiss()
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}


