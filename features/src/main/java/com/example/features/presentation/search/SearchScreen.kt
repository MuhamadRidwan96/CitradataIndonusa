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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.core_ui.component.AddressTextField
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
import kotlinx.coroutines.delay


// New state holder class for better state management
class SearchState {
    var withPpr by mutableStateOf(false)
    var query by mutableStateOf("")
    var selectedCategoryId by mutableStateOf<Int?>(null)
    var address by mutableStateOf("")
    var isLoading by mutableStateOf(true) // Tambbahkan
}


// Added focus management and better state organization
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController) {
    val searchState = rememberSearchState()
    var canRenderContent by remember { mutableStateOf(false) }

    // Simulasikan delay agar komposisi berat dilakukan setelah frame pertama
    LaunchedEffect(Unit) {
        delay(1000)
        searchState.isLoading = false
        canRenderContent = true
    }

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
        if (searchState.isLoading || !canRenderContent) {
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
                searchState = searchState,
                onClick = {}
            )
        }
    }
}

// Extracted to separate composable for better organization
@Composable
private fun SearchContent(
    paddingValues: PaddingValues,
    navController: NavController,
    searchState: SearchState,
    onClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .semantics { contentDescription = "Search Screen Content" },  // Added for accessibility
        contentPadding = PaddingValues(bottom = 100.dp)  // Moved spacer to contentPadding
    ) {
        item {
            SwitchPpr(
                checked = searchState.withPpr,
                onCheckedChange = { searchState.withPpr = it }
            )
        }
        item {
            NavigationButtonRow(
                navController = navController
            )
        }
        item {
            SearchHeaderSection(
                searchState = searchState
            )
        }
        item {
            LocationDropdownSection(
                addressState = searchState.address,
                onValueChange = { searchState.address = it },
                modifier = Modifier.padding(vertical = 8.dp)  // Consistent padding
            )
        }
        item {
            CategoryFilterSection(
                selectedCategoryId = searchState.selectedCategoryId,
                onCategorySelected = { searchState.selectedCategoryId = it }
            )
        }

        item {
            SearchButton(onClick = onClick)
        }
    }
}

// New state holder class for better state management

@Composable
private fun rememberSearchState() = remember {
    SearchState()
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

// Enhanced search header section
@Composable
private fun SearchHeaderSection(
    searchState: SearchState,
) {
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
                query = searchState.query,
                onQueryChange = { searchState.query = it },
                onSearchClicked = {}
            )
            Spacer(modifier = Modifier.height(10.dp))
            StartAndEndDate()
            Spacer(modifier = Modifier.height(10.dp))
            ProjectStatusCategory()
        }
    }
}

// Improved search bar component


// More focused location dropdown section
@Composable
private fun LocationDropdownSection(
    addressState: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    var selectedProvinceId by remember { mutableStateOf<String?>(null) }

    val provinceViewModel: ProvinceViewModel = hiltViewModel()
    val cityViewModel: CityViewModel = hiltViewModel()

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
                    selectedProvinceId = idProvince
                },
            )

            Spacer(modifier = Modifier.height(8.dp))
            CityBottomSheet(
                onDismiss = { },
                viewModel = cityViewModel,
                idProvince = selectedProvinceId,
                onClear = {
                    cityViewModel.updateCity("", null, "")
                    selectedProvinceId = null
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            AddressTextField(
                value = addressState,
                onValueChange = { onValueChange }
            )
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











