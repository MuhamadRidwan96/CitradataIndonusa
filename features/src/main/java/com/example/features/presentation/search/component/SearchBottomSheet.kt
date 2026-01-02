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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.SwitchPpr
import com.example.features.presentation.search.state.LocationState
import com.example.features.presentation.search.state.ProjectFilterState
import com.example.features.presentation.search.state.SearchBottomSheetAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    searchState: ProjectFilterState,
    locationState: LocationState,
    selectedProvince: String,
    selectedCity: String,
    onGetProvince : (String) -> Unit,
    onGetCity : (String) -> Unit,
    onAction: (SearchBottomSheetAction) -> Unit

) {

    ModalBottomSheet(
        onDismissRequest = { onAction(SearchBottomSheetAction.Dismiss) },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        SearchBottomSheetContent(
            searchState = searchState,
            locationState = locationState,
            selectedProvince = selectedProvince,
            selectedCity = selectedCity,
            onAction = onAction,
            onGetProvince = onGetProvince,
            onGetCity = onGetCity,
        )
    }
}

@Composable
private fun SearchBottomSheetContent(
    searchState: ProjectFilterState,
    locationState: LocationState,
    selectedProvince: String,
    selectedCity: String,
    onGetProvince : (String) -> Unit,
    onGetCity : (String) -> Unit,
    onAction: (SearchBottomSheetAction) -> Unit
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
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge
            )
        }
        item {
            SwitchPpr(
                checked = searchState.withPpr,
                onCheckedChange = {
                    onAction(SearchBottomSheetAction.SetWithPpr(it))
                }
            )
        }
        item {
            StartAndEndDate(
                startDate = searchState.startDate,
                endDate = searchState.endDate,
                onStartDateSelected = {date ->
                    onAction(SearchBottomSheetAction.SetStartDate(date))},
                onEndDateSelected = { endDate ->
                    onAction(SearchBottomSheetAction.SetEndDate(endDate))
                },
                onClearStartDate = {onAction(SearchBottomSheetAction.ClearStartDate)},
                onClearEndDate = { onAction(SearchBottomSheetAction.ClearEndDate) }
            )
        }

        item {
            LocationSection(
                query = searchState.address,
                onQueryChange = { query ->
                    onAction(SearchBottomSheetAction.QueryChange(query))
                },
                onProvinceSelect = { idProvince,name ->
                    onAction(SearchBottomSheetAction.SelectProvince(idProvince,name))
                },
                onCitySelect = {idCity,idProvince,name ->
                    onAction(SearchBottomSheetAction.SelectCity(idCity,idProvince,name))
                },
                onGetProvince = onGetProvince,
                onGetCity = onGetCity,
                state = locationState,
                selectedProvince = selectedProvince,
                selectedCity = selectedCity,
                idProvince = searchState.idProvince,
            )
        }

        item {
            ProjectCategory(
                categorySelected = searchState.idProjectCategory,
                onCategorySelect = { id,name ->
                    onAction(SearchBottomSheetAction.SelectProjectCategory(id,name)) }
            )
        }
        item {
            BuildingCategory(
                selectedCategoryId = searchState.idBuildingCategory,
                onCategorySelected = {id,name ->
                    onAction(SearchBottomSheetAction.SelectBuildingCategory(id,name))
                }
            )
        }

        item {
            ProjectStatusCategory(
                modifier = Modifier.padding(horizontal = 16.dp),
                onStatusSelected = { id,name ->
                    onAction(SearchBottomSheetAction.SelectStatus(id,name)) },
                selectedStatusId = searchState.idProjectStatusCategory,
            )
        }

        item {

            SimpleButton(
                text = stringResource(R.string.cari),
                onClick = {
                    onAction(SearchBottomSheetAction.Apply)
                    onAction(SearchBottomSheetAction.Dismiss)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

