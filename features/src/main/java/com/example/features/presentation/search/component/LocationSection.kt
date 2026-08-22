package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.R
import com.example.core_ui.component.CDATextField
import com.example.features.presentation.search.state.ProjectFilterState
import com.example.features.presentation.search.state.location.LocationUiAction
import com.example.features.presentation.search.state.search.SearchUiAction
import com.example.features.presentation.search.viewmodel.LocationViewModel

@Composable
fun LocationSection(
    searchState: ProjectFilterState,
    onAction: (SearchUiAction) -> Unit,
    modifier: Modifier = Modifier,
    locationVM: LocationViewModel = hiltViewModel()
) {

    val state by locationVM.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.pin),
                contentDescription = "location",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(R.string.location),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
            )
        }

        ProvinceBottomSheet(
            selectedProvince = searchState.provinceName,
            onGetProvince = {
                locationVM.action(
                    LocationUiAction.LoadProvince
                )
            },
            onProvinceSelect = { id, name ->

                // Update filter/search state
                onAction(
                    SearchUiAction.SetProvince(id, name)
                )
            },
            state = state,
        )

        CityBottomSheet(
            idProvince = searchState.idProvince,
            selectedCityName = searchState.cityName,
            onCitySelect = { id, idProvince, city ->
                onAction(SearchUiAction.SetCity(id, idProvince, city))
            },
            onGetCity = { idProvince ->
                locationVM.action(
                    LocationUiAction.LoadCity(idProvince ?: "")
                )
            },
            state = state,
        )

        CDATextField(
            icon = R.drawable.map_pin_house,
            value = searchState.address,
            onValueChange = {
                onAction(
                    SearchUiAction.QueryChanged(it)
                )
            },
            placeholder = stringResource(R.string.masukan_alamat)
        )
    }
}
