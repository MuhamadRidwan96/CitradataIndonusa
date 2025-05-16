package com.example.features.presentation.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.CardItem
import com.example.core_ui.component.Carousel
import com.example.core_ui.component.CarouselItem
import com.example.core_ui.component.FilterCategory
import com.example.core_ui.component.FilterCategoryRow
import com.example.core_ui.component.ProjectCard
import com.example.core_ui.component.SearchBars
import com.example.feature_login.R
import com.example.features.presentation.home.component.TopAppBarContent
import kotlinx.coroutines.delay

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { TopAppBarContent(imageVector = Icons.Default.Notifications) }
    ) { paddingValues ->
        HomeContent(paddingValues)
    }
}

@Composable
private fun HomeContent(paddingValues: PaddingValues) {

    var showList by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(1500)
        showList = true
    }

    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        SearchSection()
        Spacer(modifier = Modifier.size(32.dp))
        CarouselSection()
        Spacer(modifier = Modifier.size(24.dp))
        CategoryFilterSection()

        if (showList) {
            TestList(listState = listState)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        Spacer(modifier = Modifier.size(75.dp))
    }
}

@Composable
private fun SearchSection() {
    // Move state management higher if used elsewhere
    var query by remember { mutableStateOf("") }
    SearchBars(
        query = query,
        onQueryChange = { query = it },
        modifier = Modifier.padding(horizontal = 16.dp)

    )
}

@Composable
private fun CarouselSection() {

    // Use remember with keys if these items might change
    val dummyItems = remember {
        listOf(
            CarouselItem(
                imageRes = R.drawable.carousel_001,
                status = "Baru",
                date = "3 Mei 2025",
                title = "Proyek Jalan Tol",
                location = "Jakarta",
                category = "Konstruksi"
            ),
            CarouselItem(
                imageRes = R.drawable.carousel_002,
                status = "Sedang Berjalan",
                date = "1 Mei 2025",
                title = "Jembatan Baru",
                location = "Bandung",
                category = "Infrastruktur"
            ),
            CarouselItem(
                imageRes = R.drawable.carousel_003,
                status = "Sedang Berjalan",
                date = "1 Mei 2025",
                title = "Jembatan Baru",
                location = "Bandung",
                category = "Infrastruktur"
            )
        )
    }

    // Limit carousel height to prevent excessive rendering
    Carousel(
        items = dummyItems,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp) // Fixed height for better performance
    )
}


@Composable
fun CategoryFilterSection() {

    // Extract categories list to avoid recreation on recomposition
    val categories = remember {
        listOf(
            FilterCategory(5,"Highrise & Commercial", "HRC", Icons.Default.Apartment),
            FilterCategory(6,"Middle Project", "MDL", Icons.Default.Home),
            FilterCategory(7,"Low Project", "LOW", Icons.Default.House),
            FilterCategory(8,"Industrial & Infrastructure", "IND", Icons.Default.Factory),
            FilterCategory(9,"Fitting Out & Interior", "FTO", Icons.Default.Store)
        )
    }

    // State hoisted to parent if needed elsewhere
    var selectedCategory by rememberSaveable { mutableStateOf<Int?>(5) }

    FilterCategoryRow(

        categories = categories,
        selectedCategoryProjectId = selectedCategory,
        onCategoryProjectSelected = { selectedCategory = it },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun TestList(listState: LazyListState) {

    val favoriteMap = remember { mutableStateMapOf<String, Boolean>() }

    val list = remember {
        listOf(
            CardItem(
                title = "PASAR INDUK BANYUWANGI DAN ASRAMA INGGRISAN BANYUWANGI (REVITALISASI)",
                date = "May,15-2025",
                category = "Middle Project",
                location = "Ngawi, East Java",
                statusProject = "New",
                status = "Planning",
            ),
            CardItem(
                title = "Harbor Tower Development",
                date = "Jun 15, 2023",
                category = "Highrise and commercial building",
                location = "Downtown, Tokyo",
                status = "Finish",
                statusProject = "Update",
            ),
            CardItem(
                title = "HOSPITAL – RS. MAYAPADA JAKARTA TIMUR",
                date = "Jun 15, 2023",
                category = "Highrise and commercial building",
                location = "Downtown, Tokyo",
                status = "Construction Start",
                statusProject = "New",
            ),
            CardItem(
                title = "OFFICE - GEDUNG DAN KAWASAN PERKANTORAN KEMENTERIAN PERTAHANAN IKN NUSANTARA (TAHAP 1)",
                date = "Jun 15, 2023",
                category = "Industrial and Construction",
                location = "Downtown, Tokyo",
                status = "Post Tender",
                statusProject = ""
            ),
            CardItem(
                title = "HOTEL - HOTEL MAWAR MELATI",
                date = "Jun 15, 2023",
                category = "Middle Project",
                location = "Downtown, Tokyo",
                status = "Under Construction",
                statusProject = ""
            )
        )
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = list, key = { it.title }, contentType = { "project_card" }) { item ->
            val isFavorite = favoriteMap[item.title] ?: false
            ProjectCard(
                items = item,
                isFavorite = isFavorite,
                onFavoriteClick = {
                    favoriteMap[item.title] = !isFavorite
                }
            )
        }
    }
}
