package com.example.features.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core_ui.component.CompactSearchBar
import com.example.core_ui.component.SwitchPpr
import com.example.feature_login.R
import com.example.features.nav.graph.SearchRoutes
import com.example.features.presentation.search.component.ButtonRow
import com.example.features.presentation.search.component.CityBottomSheet
import com.example.features.presentation.search.component.ProjectStatusCategory
import com.example.features.presentation.search.component.ProvinceBottomSheet
import com.example.features.presentation.search.component.SearchChips
import com.example.features.presentation.search.component.StartAndEndDate
import com.example.features.presentation.search.viewmodel.CityViewModel
import com.example.features.presentation.search.viewmodel.ProvinceViewModel
import com.example.features.presentation.search.viewmodel.SearchViewModel
import kotlinx.coroutines.delay

// Added focus management and better state organization
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.searchState.collectAsState()

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

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {
            SearchContent(
                paddingValues = paddingValues,
                navController = navController,
                onClick = {}
            )
        }
    }

    viewModel.setLoading(false)
}


// Extracted to separate composable for better organization
@Composable
private fun SearchContent(
    paddingValues: PaddingValues,
    navController: NavController,
    onClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val provinceViewModel: ProvinceViewModel = hiltViewModel()
    val cityViewModel: CityViewModel = hiltViewModel()

    val filterState by viewModel.searchState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .semantics { contentDescription = "Search Screen Content" },  // Added for accessibility
        contentPadding = PaddingValues(bottom = 100.dp)  // Moved spacer to contentPadding
    ) {
        item {
            SwitchPpr(
                checked = filterState.withPpr == "ppr",
                onCheckedChange = viewModel::updatePpr
            )
        }
        item { NavigationButtonRow(navController = navController) }
        item { SearchHeaderSection() }
        item {
            LocationDropdownSection(
                addressState = filterState.address,
                idProvince = filterState.idProvince,
                idCity = filterState.idCity,
                onCitySelected = { selectedCity ->
                    viewModel.updateFilterState { copy(idCity = selectedCity) }
                },
                onProvinceSelected = { selectedProvince ->
                    viewModel.updateFilterState { copy(idProvince = selectedProvince) }
                },
                onAddressChange = { viewModel.updateFilterState { copy(address = it) } },
                modifier = Modifier.padding(vertical = 8.dp),
                onClear = { viewModel.updateFilterState { copy(idCity = "", idProvince = "") } },
                provinceViewModel = provinceViewModel,
                cityViewModel = cityViewModel,
            )
        }
        item {
            CategoryFilterSection(
                selectedCategoryId = filterState.idProjectCategory,
                onCategorySelected = { viewModel.updateFilterState { copy(idProjectCategory = it) } }
            )
        }

        item {
            SearchButton(onClick = onClick)
        }
    }
}


// Enhanced search header section
@Composable
private fun SearchHeaderSection(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val filterState by viewModel.searchState.collectAsState()
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)  // Consistent padding
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,  // Using theme shape
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary // Better color contrast
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)  // Reduced elevation
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            CompactSearchBar(
                query = filterState.query,
                onQueryChange = { viewModel.updateFilterState { copy(query = it) } },
                onSearchClicked = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
            StartAndEndDate(
                startDate = filterState.startDate,
                endDate = filterState.endDate,
                onStartDateSelected = { viewModel.updateFilterState { copy(startDate = it) } },
                onEndDateSelected = { viewModel.updateFilterState { copy(endDate = it) } },
                onClearStartDate = { viewModel.updateFilterState { copy(onClearStartDate = "") } },
                onClearEndDate = { viewModel.updateFilterState { copy(onClearEndDate = "") } }
            )
            Spacer(modifier = Modifier.height(10.dp))
            ProjectStatusCategory()
        }
    }
}

@Composable
private fun LocationDropdownSection(
    addressState: String,
    idProvince: String?,
    idCity: String?,
    onAddressChange: (String) -> Unit,
    onProvinceSelected: (String) -> Unit,
    onCitySelected: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    provinceViewModel: ProvinceViewModel,
    cityViewModel: CityViewModel

) {

    Card(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            ProvinceBottomSheet(
                onDismiss = {},
                viewModel = provinceViewModel,
                onProvinceSelected = { idProvince ->
                    onProvinceSelected(idProvince)
                },
                onClear = onClear

            )

            Spacer(modifier = Modifier.height(8.dp))
            CityBottomSheet(
                onDismiss = { },
                viewModel = cityViewModel,
                idProvince = idProvince,
                onClear = {
                    onClear()
                    cityViewModel.updateCity("", null, "")
                    provinceViewModel.updateProvince("", "")
                },
                onCitySelected = { idCity ->
                    onCitySelected(idCity)
                },
                selectedCity = idCity
            )
            Spacer(modifier = Modifier.height(8.dp))
            /*AppOutlinedTextFieldEnable(
                value = addressState,
                onValueChange = { onAddressChange(it)},
                modifier = TODO(),
                onClearClicked = TODO(),
                placeHolder = TODO(),
                onClicked = TODO(),
                enable = TODO()
            )*/
        }
    }
}

// Enhanced category filter section
@Composable
private fun CategoryFilterSection(
    selectedCategoryId: Int?,
    onCategorySelected: (Int?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Pilih Kategori Bangunan",
            style = MaterialTheme.typography.titleMedium,  // Better padding
            color = MaterialTheme.colorScheme.onSurface  // Proper text color
        )
        SearchChips(
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = onCategorySelected  // Added bottom padding
        )

        selectedCategoryId?.let { id ->
            Text(
                text = "Kategori terpilih: $id", // Fixed text display
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        }
    }
}

@Composable
private fun SearchButton(onClick: () -> Unit) {
    val planStyle = MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onPrimary,
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(2.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
        ) {
            Text("Cari", style = planStyle)
        }
    }
}

// Improved button row with better type safety
@Composable
private fun NavigationButtonRow(
    navController: NavController
) {
    val buttonTitles = remember {
        listOf(
            "Consultant" to SearchRoutes.CONSULTANT,
            "Developer" to SearchRoutes.DEVELOPER,
            "Contractor" to SearchRoutes.CONTRACTOR
        )
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 18.dp), // Increased padding
        horizontalArrangement = Arrangement.SpaceBetween  // Added spacing between buttons
    ) {
        items(buttonTitles) { (title, route) ->
            ButtonRow(
                title = title,
                onClick = { navController.navigate(route) }// Fixed width for consistency
            )
        }
    }
}









