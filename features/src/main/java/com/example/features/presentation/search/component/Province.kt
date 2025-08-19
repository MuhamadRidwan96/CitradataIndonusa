package com.example.features.presentation.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import com.example.common.Result
import com.example.core_ui.component.AppBottomSheet
import com.example.core_ui.component.CompactSearchBar
import com.example.domain.response.ProvinceResponse
import com.example.features.presentation.search.viewmodel.ProvinceEvent
import com.example.features.presentation.search.viewmodel.ProvinceViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ProvinceBottomSheet(
    onDismiss: () -> Unit,
    viewModel: ProvinceViewModel,
    onProvinceSelected: (String) -> Unit,
    onClear: () -> Unit
) {

    var showListProvince by rememberSaveable { mutableStateOf(false) }
    val query by viewModel.query
    val provinces by viewModel.provinceList.collectAsState()
    val provinceState by viewModel.provinceStateViewModel.collectAsState()
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
       /* AppOutlinedTextFieldEnableFalse(
            value = provinceState.provinceName,
            onClearClicked = {
                onClear()
                viewModel.updateProvince("", "")
            },
            placeHolder = "Pilih Provinsi",
            onClicked = { showListProvince = true }
        )*/

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
                    .heightIn(400.dp)
                    .padding(16.dp)
            ) {
                CompactSearchBar(
                    query = query,
                    onQueryChange = viewModel::onQueryChange
                )

                // Filtered province list
                val filteredProvinces = when (provinces) {
                    is Result.Success -> {
                        val allProvinces = (provinces as Result.Success<ProvinceResponse>).data.data
                        allProvinces.filter {
                            it.province.contains(query, ignoreCase = true)
                        }
                    }
                    else -> emptyList()
                }

                LazyColumn(
                    modifier = Modifier.heightIn(400.dp)
                ) {
                    items(filteredProvinces) { province ->
                        Text(
                            text = province.province,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateProvince(
                                        id = province.idProvince,
                                        name = province.province
                                    )
                                    onProvinceSelected(province.idProvince)
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


