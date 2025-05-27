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
import com.example.features.presentation.search.CityEvent
import com.example.features.presentation.search.CityViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CityBottomSheet(
    idProvince: String?,
    onDismiss: () -> Unit,
    viewModel: CityViewModel,
    onClear:()->Unit
) {
    var showListCity by rememberSaveable { mutableStateOf(false) }
    val query by viewModel.query
    val cities by viewModel.cityList.collectAsState()
    val searchState by viewModel.cityState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showListCity, idProvince) {
        if (showListCity) {
            viewModel.getCity(idProvince)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cityEvent.collectLatest { event ->
            when (event) {
                is CityEvent.Success -> {}
                is CityEvent.Error -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }

        }
    }

    Column {
        AppOutlinedTextFieldEnableFalse(
            value = searchState.cityName,
            onClearClicked = onClear,
            placeHolder = "Pilih Kota",
            onClicked = { showListCity = true },
        )

        AppBottomSheet(
            isVisible = showListCity,
            onDismiss = {
                showListCity = false
                onDismiss()
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                CompactSearchBar(
                    query = query,
                    onSearchClicked = {},
                    onQueryChange = { viewModel::onQueryChange }
                )

                val filteredCityList = cities.filter {
                    it.cityName.contains(query.trim(), ignoreCase = true)
                }

                LazyColumn {
                    items(filteredCityList) { city ->
                        Text(
                            text = city.cityName,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.updateCity(
                                        idCity = city.idCity,
                                        idProvince = city.idProvince,
                                        name = city.cityName
                                    )
                                    showListCity = false
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