package com.example.features.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.SearchAbleProvinceDropDown
import com.example.domain.model.ProvinceModel

@Composable
fun DropdownCity() {
    val province by produceState(initialValue = emptyList()) {
        value = listOf(
            ProvinceModel(11, "NANGGRO ACEH DARUSALAM"),
            ProvinceModel(12, "SUMATERA UTARA"),
            ProvinceModel(13, "SUMATERA BARAT"),
            ProvinceModel(14, "RIAU"),
            ProvinceModel(15, "JAMBI")
        )
    }

    var selectedProvince by rememberSaveable { mutableStateOf<ProvinceModel?>(null) }
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        SearchAbleProvinceDropDown(
            provinces = province,
            selectedProvince = selectedProvince,
            onSelectedProvince = { selectedProvince = it }
        )
        Spacer(modifier = Modifier.height(16.dp))

        selectedProvince?.let {
            Text("ID: ${it.idProvince}, Nama: ${it.name}")
        }
    }
}