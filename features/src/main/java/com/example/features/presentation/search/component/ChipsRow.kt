package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.features.presentation.search.state.ProjectFilterState
import com.example.features.presentation.search.utils.hasValue
import com.example.features.presentation.search.utils.hasValueInt

@Composable
fun ChipsRow(
    modifier: Modifier = Modifier,
    searchState: ProjectFilterState,
    clearPpr:() -> Unit,
    clearDateRange:() -> Unit,
    clearStatus:() -> Unit,
    clearBuilding:() -> Unit,
    clearProvince:() -> Unit,
    clearCity:() -> Unit,
    clearCategory : () -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        if (searchState.withPpr) {
            item {
                Chip(
                    label = {
                        Text(
                            "PPR",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = { clearPpr() }
                )
            }
        }

        if (searchState.startDate.hasValue() && searchState.endDate.hasValue()) {
            item {
                Chip(
                    label = {
                        Text(
                            text = searchState.startDate + "-" + searchState.endDate,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = { clearDateRange() }
                )
            }
        }

        if (searchState.idProjectStatusCategory.hasValueInt()) {
            item {
                Chip(
                    label = {
                        Text(
                            searchState.statusCategory.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = { clearStatus() }
                )
            }
        }

        if (searchState.idBuildingCategory.hasValueInt()) {
            item {
                Chip(
                    label = {
                        Text(
                            searchState.buildingCategoryName.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = { clearBuilding() }
                )
            }
        }

        if (searchState.idProvince.hasValue()) {
            item {
                Chip(
                    label = {
                        Text(
                            searchState.provinceName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        clearProvince()
                    }
                )
            }
        }

        if (searchState.idCity.hasValue()) {
            item {
                Chip(
                    label = {
                        Text(
                            searchState.cityName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        clearCity()
                    })
            }
        }

        if (searchState.idProjectCategory.hasValueInt()) {
            item {
                Chip(
                    label = {
                        Text(
                            searchState.projectCategoryName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onClick = {
                        clearCategory()
                    }
                )
            }
        }
    }
}