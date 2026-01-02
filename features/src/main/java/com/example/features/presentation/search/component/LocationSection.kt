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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.CDATextField
import com.example.features.presentation.search.state.LocationState

@Composable
fun LocationSection(
    query: String,
    selectedProvince: String,
    selectedCity: String,
    idProvince: String?,
    onQueryChange: (String) -> Unit,
    onProvinceSelect: (String?, String) -> Unit,
    onCitySelect: (String?, String?, String) -> Unit,
    modifier: Modifier = Modifier,
    onGetProvince: (String) -> Unit,
    onGetCity: (String) -> Unit,
    state: LocationState
) {

    var localQuery by remember { mutableStateOf(query) }

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
            selectedProvince = selectedProvince,
            onGetProvince = { onGetProvince("") },
            onProvinceSelect = onProvinceSelect,
            state = state,
        )

        CityBottomSheet(
            idProvince = idProvince,
            selectedCityName = selectedCity,
            onCitySelect = onCitySelect,
            onGetCity = onGetCity,
            state = state,
        )

        CDATextField(
            icon = R.drawable.map_pin_house,
            value = localQuery,
            onValueChange = {
                localQuery = it
                onQueryChange(it)
            },
            placeholder = stringResource(R.string.masukan_alamat)
        )
    }
}
