package com.example.features.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.AddressTextField
import com.example.core_ui.component.SearchBars
import com.example.core_ui.component.SwitchPpr
import com.example.feature_login.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.Search),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
    ) { paddingValues ->
        SearchContent(paddingValues)
    }
}

@Composable
fun SearchContent(paddingValues: PaddingValues) {
    var withPpr by rememberSaveable { mutableStateOf(false) }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        item {
            SwitchPpr(
                checked = withPpr,
                onCheckedChange = { withPpr = it }
            )
        }
        item { HeaderSearchSection() }
        item { DropdownSection() }
        item { ChipSection() }
    }
}

@Composable
private fun SearchSections() {
    var query by remember { mutableStateOf("") }
    SearchBars(
        query = query,
        onQueryChange = { query = it },
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}



@Composable
private fun ChipSection() {
    var selectedCategoryId by rememberSaveable { mutableStateOf<Int?>(null) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Pilih Kategori Bangunan",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(6.dp)
        )

        SearchChips(
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = { selectedCategoryId = it }
        )
    }
}

@Composable
private fun HeaderSearchSection() {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SearchSections()
            StartAndEndDate()

        }
    }
}

@Composable
private fun DropdownSection(

) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {
        Column {
            val address = remember { mutableStateOf("") }

            DropdownProvince()
            DropdownCity()
            AddressTextField(
                value = address.value,
                onValueChange = { address.value = it }
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}






