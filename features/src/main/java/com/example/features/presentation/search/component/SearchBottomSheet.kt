package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.SwitchPpr
import com.example.features.presentation.search.state.ProjectFilterState
import com.example.features.presentation.search.viewmodel.CityViewModel
import com.example.features.presentation.search.viewmodel.ProvinceViewModel
import com.example.features.presentation.search.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    viewModel: SearchViewModel,
    provinceVM: ProvinceViewModel,
    cityVM: CityViewModel
) {
    val searchState by viewModel.draftState.collectAsState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        SearchBottomSheetContent(
            searchState = searchState,
            onSetWithPpr = { enabled ->
                viewModel.updateDraft {
                    it.copy(
                        withPpr = enabled,
                        ppr = "PPR"
                    )
                }
            },
            onSetStartDate = { startDate -> viewModel.updateDraft { it.copy(startDate = startDate) } },
            onSetEndDate = { endDate -> viewModel.updateDraft { it.copy(endDate = endDate) } },
            onClearStartDate = { viewModel.updateDraft { it.copy(startDate = "") } },
            onClearEndDate = { viewModel.updateDraft { it.copy(endDate = "") } },
            onStatusSelected = { id, status ->
                viewModel.updateDraft {
                    it.copy(
                        idProjectStatusCategory = id,
                        statusCategory = status
                    )
                }
            },
            onCategorySelected = { id, category ->
                viewModel.updateDraft {
                    it.copy(
                        idBuildingCategory = id,
                        buildingCategoryName = category
                    )
                }
            },
            onQueryChanged = { newAddress ->
                viewModel.updateDraft {
                    it.copy(address = newAddress)
                }
            },
            onProvinceSelected = { idProvince, province ->
                viewModel.updateDraft {
                    it.copy(
                        idProvince = idProvince,
                        provinceName = province
                    )
                }
            },
            onCitySelected = { idCity, idProvince, city ->
                viewModel.updateDraft {
                    it.copy(
                        idCity = idCity,
                        idProvince = idProvince,
                        cityName = city
                    )
                }
            },
            provinceViewModel = provinceVM,
            cityViewModel = cityVM,
            searchViewModel = viewModel,
            onDismiss = onDismiss,
            onCategoryProjectSelected = { idProjectCat, cat ->
                viewModel.updateDraft {
                    it.copy(
                        idProjectCategory = idProjectCat,
                        projectCategoryName = cat
                    )
                }
            },
        )
    }
}

@Composable
private fun SearchBottomSheetContent(
    searchState: ProjectFilterState,
    onSetWithPpr: (Boolean) -> Unit,
    onSetStartDate: (String) -> Unit,
    onSetEndDate: (String) -> Unit,
    onClearStartDate: () -> Unit,
    onClearEndDate: () -> Unit,
    onStatusSelected: (Int?, String) -> Unit,
    onCategorySelected: (Int?, String) -> Unit,
    onQueryChanged: (String) -> Unit,
    onProvinceSelected: (String?, String) -> Unit,
    onCitySelected: (String?, String?, String) -> Unit,
    provinceViewModel: ProvinceViewModel,
    cityViewModel: CityViewModel,
    searchViewModel: SearchViewModel,
    onDismiss: () -> Unit,
    onCategoryProjectSelected: (Int?, String) -> Unit

) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Text(
                text = stringResource(R.string.search_category),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge
            )
        }
        item {
            SwitchPpr(
                checked = searchState.withPpr,
                onCheckedChange = onSetWithPpr
            )
        }
        item {
            StartAndEndDate(
                startDate = searchState.startDate,
                endDate = searchState.endDate,
                onStartDateSelected = onSetStartDate,
                onEndDateSelected = onSetEndDate,
                onClearStartDate = onClearStartDate,
                onClearEndDate = onClearEndDate
            )
        }

        item {
            LocationSection(
                query = searchState.address,
                onQueryChange = onQueryChanged,
                onProvinceSelected = onProvinceSelected,
                onCitySelected = onCitySelected,
                provinceViewModel = provinceViewModel,
                cityViewModel = cityViewModel
            )
        }

        item {
            ProjectCategory(
                categorySelected = searchState.idProjectCategory,
                onCategorySelected = onCategoryProjectSelected
            )
        }
        item {
            BuildingCategory(
                selectedCategoryId = searchState.idBuildingCategory,
                onCategorySelected = onCategorySelected
            )
        }

        item {
            ProjectStatusCategory(
                modifier = Modifier.padding(horizontal = 16.dp),
                onStatusSelected = onStatusSelected,
                selectedStatusId = searchState.idProjectStatusCategory,
            )
        }

        item {

            SimpleButton(
                text = stringResource(R.string.cari),
                onClick = {
                    searchViewModel.applyFilters()
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}
