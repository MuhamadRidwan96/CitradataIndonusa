package com.example.features.presentation.home.screen.dashboard

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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_ui.component.CompactSearchBar
import com.example.core_ui.component.FilterCategory
import com.example.core_ui.component.FilterCategoryRow
import com.example.data.utils.TokenExpiredException
import com.example.features.presentation.home.DataEvent
import com.example.features.presentation.home.HomeViewModel
import com.example.features.presentation.home.component.ProjectCard
import com.example.features.presentation.home.component.TopAppBarContent
import com.example.features.presentation.home.state.toDataState
import com.example.features.presentation.profile.LogOutViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewmodel: HomeViewModel = hiltViewModel(),
    logoutViewmodel: LogOutViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onNavigateToLogin: () -> Unit,
    onNavigateToDetail: (String) -> Unit
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val pagingItems = viewmodel.currentPagingData.collectAsLazyPagingItems()
    val profile = viewmodel.userProfile
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showErrorSheet by remember { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableIntStateOf(4) }

    LaunchedEffect(pagingItems.loadState) {
        val error = pagingItems.loadState.refresh as? LoadState.Error
        if (error?.error is TokenExpiredException) {
            showErrorSheet = true
        }
    }

    if (showErrorSheet) {
        ErrorBottomSheet(
            message = "Sesi anda telah berakhir, silahkan login kembali!",
            sheetState = sheetState,
            onDismiss = {
                coroutineScope.launch {
                    onNavigateToLogin()
                    logoutViewmodel.logout()
                    showErrorSheet = false

                }
            }
        )
    }

    // Listen to UI Events
    LaunchedEffect(Unit) {
        viewmodel.dataEvent.collect { event ->
            when (event) {
                is DataEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }

                is DataEvent.Success -> {}
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBarContent(
                imageVector = Icons.Default.Notifications,
                photo = profile?.photo ?: "",
                email = profile?.email ?: "",
                name = profile?.name ?: ""
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            SearchSection(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    viewmodel.applyProjectName(mapOf("project_name" to it))
                }
            )

            CategoryFilterSection(
                selectedCategory = selectedCategory,
                onCategorySelected = { category ->
                    selectedCategory = category
                    viewmodel.applyCategories(getCategoryCode(category))
                }
            )

            val listState = rememberSaveable(
                saver = LazyListState.Saver
            ) { LazyListState() }

            LazyColumn(
                contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding() + 50.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                state = listState

            ) {
                items(pagingItems.itemCount) { index ->
                    val recordData = pagingItems[index]
                    recordData?.let {
                        val no = index + 1
                        val dataState = it.toDataState(uiState.isFavorite, no)

                        ProjectCard(
                            project = dataState,
                            onFavoriteClick = { id ->
                                viewmodel.favoriteChecked(id)
                            },
                            onClick = { onNavigateToDetail(dataState.idProject) }
                        )
                    }
                }
                pagingItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { LoadingItem() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingItem() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val e = (loadState.refresh as LoadState.Error).error
                            if (e !is TokenExpiredException) {
                                item {
                                    PagingErrorItem("Gagal memuat data")
                                }
                            }
                        }

                        loadState.append is LoadState.Error -> {
                            val e = (loadState.append as LoadState.Error).error
                            if (e !is TokenExpiredException) {
                                item {
                                    PagingErrorItem("Tidak ada data berikutnya!")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CategoryFilterSection(
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit
) {
    val categories = remember {
        listOf(
            FilterCategory(5, "Highrise & Commercial", "HRC", Icons.Default.Apartment),
            FilterCategory(6, "Middle Project", "MDL", Icons.Default.Home),
            FilterCategory(7, "Low Project", "LOW", Icons.Default.House),
            FilterCategory(8, "Industrial & Infrastructure", "IND", Icons.Default.Factory),
            FilterCategory(9, "Fitting Out & Interior", "FTO", Icons.Default.Store)
        )
    }

    FilterCategoryRow(
        categories = categories,
        selectedCategoryProjectId = selectedCategory,
        onCategoryProjectSelected = { categoryId ->
            onCategorySelected(categoryId)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

private fun getCategoryCode(categoryId: Int): String {
    return when (categoryId) {
        5 -> "5"
        6 -> "6"
        7 -> "7"
        8 -> "8"
        9 -> "9"
        else -> ""
    }
}

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorBottomSheet(
    message: String,
    onDismiss: () -> Unit,
    sheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = onDismiss) {
                Text("Tutup")
            }
        }
    }
}

@Composable
fun PagingErrorItem(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            color = Color.Red,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun SearchSection(
    query: String,
    onQueryChange: (String) -> Unit
) {
    CompactSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()

    )
}




