package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.SwitchPpr
import com.example.features.presentation.search.state.ProjectFilterState
import com.example.features.presentation.search.state.search.SearchUiAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun SearchBottomSheet(
        modifier: Modifier = Modifier,
        onAction: (SearchUiAction) -> Unit,
        onDismiss: () -> Unit,
        searchState: ProjectFilterState


    ) {

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier.fillMaxWidth()
    ) {
      SearchBottomSheetContent(
          searchState = searchState,
          onAction = onAction
      ) 
    }
}

@Composable
private fun SearchBottomSheetContent(
    searchState: ProjectFilterState,
    onAction: (SearchUiAction) -> Unit
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
                    onAction(SearchUiAction.SetWithPpr(it))
                }
            )
        }
        item {
            StartAndEndDate(
                startDate = searchState.startDate,
                endDate = searchState.endDate,
                onStartDateSelect = { date ->
                    onAction(SearchUiAction.SetStartDate(date))
                },
                onEndDateSelect = { endDate ->
                    onAction(SearchUiAction.SetEndDate(endDate))
                },
                onClearStartDate = { onAction(SearchUiAction.ClearDateRange) },
                onClearEndDate = { onAction(SearchUiAction.ClearDateRange) }
            )
        }

        item {
            LocationSection(
                searchState = searchState,
                onAction = onAction,
            )
        }

        item {
            ProjectCategory(
                categorySelected = searchState.idProjectCategory,
                onCategorySelect = { id, name ->
                    onAction(SearchUiAction.SetProjectCategory(id, name))
                }
            )
        }
        item {
            BuildingCategory(
                selectedCategoryId = searchState.idBuildingCategory,
                onCategorySelect = { id, name ->
                    onAction(SearchUiAction.SetBuildingCategory(id, name))
                }
            )
        }

        item {
            ProjectStatusCategory(
                modifier = Modifier.padding(horizontal = 16.dp),
                onStatusSelect = { id, name ->
                    onAction(SearchUiAction.SetStatus(id, name))
                },
                selectedStatusId = searchState.idProjectStatusCategory,
            )
        }

        item {

            SimpleButton(
                text = stringResource(R.string.cari),
                onClick = {
                    onAction(SearchUiAction.ApplyFilter)
                    //Cek lagi
                    onAction(SearchUiAction.Dismiss)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

