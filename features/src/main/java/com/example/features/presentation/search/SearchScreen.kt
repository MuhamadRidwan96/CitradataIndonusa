package com.example.features.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.core_ui.R
import com.example.data.utils.DataNotFoundException
import com.example.features.presentation.home.component.ErrorBottomSheet
import com.example.features.presentation.home.component.ProjectCard
import com.example.features.presentation.home.utils.toProjectUiItem
import com.example.features.presentation.search.component.ChipsRow
import com.example.features.presentation.search.component.LabelBackground
import com.example.features.presentation.search.component.SearchBottomSheet
import com.example.features.presentation.search.component.SearchScreenMain
import com.example.features.presentation.search.state.search.SearchUiAction
import com.example.features.presentation.search.state.search.SearchUiEvent
import com.example.features.presentation.search.utils.hasFilter
import com.example.features.presentation.search.viewmodel.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Main search screen composable that displays project search functionality
 * with filtering, pagination, and favorites management
 *
 * @param onNavigateToDetail Callback when user clicks on a project card
 * @param modifier Modifier for the root composable
 * @param snackBarHostState State holder for showing snack bar messages
 * @param viewModel ViewModel for search operations and state management
 */


@Suppress("EffectKeys")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToLogOut : () -> Unit,

    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    viewModel: SearchViewModel = hiltViewModel(),

    ) {
    // ========== State Management ==========

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Collect paginated project data as LazyPagingItems for efficient list rendering
    val lazyPagingItems = viewModel.dataPaging.collectAsLazyPagingItems()

    // Coroutine scope for launching suspending functions
    val coroutineScope = rememberCoroutineScope()

    // Controls visibility of filter bottom sheet
    var showFilterSheet by remember { mutableStateOf(false) }

    // Controls visibility of session expired bottom sheet
    var errorShowSheet by remember { mutableStateOf(false) }

    // State for controlling modal bottom sheet behavior
    val filterSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val sessionSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Local UI state for search query input
    val showDataNotFound by remember {
        derivedStateOf {
            val hasError = lazyPagingItems.loadState.refresh is LoadState.Error
            val errorIsNotFound =
                (lazyPagingItems.loadState.refresh as? LoadState.Error)?.error is DataNotFoundException

            hasError && errorIsNotFound
        }
    }


    // ========== Side Effects ==========
    LaunchedEffect(Unit, onNavigateToDetail, onNavigateToLogOut) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {

                SearchUiEvent.TokenExpired -> {
                    if (filterSheetState.isVisible) {
                        filterSheetState.hide()
                        showFilterSheet = false
                    }

                    errorShowSheet = true
                }

                is SearchUiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        event.message
                    )
                }

                is SearchUiEvent.Logout -> {
                   onNavigateToLogOut
                }

                is SearchUiEvent.NavigateToDetail -> {
                    onNavigateToDetail(state.idProject.toString())
                }
            }
        }
    }

    /* -------------------- Paging Error Handling -------------------- */

    // Monitor load state changes and handle errors

    val loadState = lazyPagingItems.loadState
    LaunchedEffect(loadState) {
        // Find the first error from refresh, append, or prepend load states
        lazyPagingItems.firstError()?.let { error ->
            viewModel.action(
                SearchUiAction.PagingError(
                    error.error
                )
            )
        }
    }


    if (errorShowSheet) {
        ErrorBottomSheet(
            message = stringResource(R.string.end_session),
            onDismiss = {
                coroutineScope.launch {
                    sessionSheetState.hide()
                    errorShowSheet = false

                    viewModel.action(
                        SearchUiAction.LogoutClicked
                    )
                }
            },
            sheetState = sessionSheetState
        )
    }


    /* -------------------- UI -------------------- */

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {

        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.searh_disc),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    modifier = Modifier.heightIn(min = 65.dp, max = 95.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    )
                )
            },
            snackbarHost = {
                CustomSnackBarHost(snackBarHostState = snackBarHostState)
            },
        ) { paddingValues ->
            if (!state.isInitialized) {
                LoadingScreen()
            } else {

                if (showFilterSheet) {
                    val focusManager = LocalFocusManager.current
                    val keyboardController = LocalSoftwareKeyboardController.current

                    val onBottomSheetAction: (SearchUiAction) -> Unit = { action ->
                        when (action) {

                            is SearchUiAction.ApplyFilter -> {
                                // Clear focus and hide keyboard BEFORE closing sheet
                                focusManager.clearFocus()
                                keyboardController?.hide()

                                coroutineScope.launch {
                                    filterSheetState.hide()
                                    showFilterSheet = false
                                }
                                viewModel.action(action)
                            }

                            else -> viewModel.action(action)
                        }
                    }
                    SearchBottomSheet(
                        onAction = onBottomSheetAction,
                        onDismiss = {
                            focusManager.clearFocus()
                            keyboardController?.hide()

                            coroutineScope.launch {
                                delay(150) // Wait for keyboard to hide
                                filterSheetState.hide()
                                showFilterSheet = false
                            }

                        },
                        searchState = state.draftFilter
                    )
                }

                Column(
                    modifier = modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    SearchScreenMain(
                        query = state.queryChange,
                        onQueryChange = { projectName ->
                            viewModel.action(
                                SearchUiAction.QueryChanged(projectName)
                            )
                        },
                        onBottomSheet = { showFilterSheet = true },
                    )

                    ChipsRow(
                        searchState = state.appliedFilter,
                        clearPpr = {
                            viewModel.action(
                                SearchUiAction.ClearPpr
                            )
                        },
                        clearDateRange = {
                            viewModel.action(
                                SearchUiAction.ClearDateRange
                            )
                        },
                        clearStatus = {
                            viewModel.action(
                                SearchUiAction.ClearStatus
                            )
                        },
                        clearBuilding = {

                            viewModel.action(
                                SearchUiAction.ClearBuilding
                            )

                        },
                        clearProvince = {
                            viewModel.action(
                                SearchUiAction.ClearProvince
                            )
                        },
                        clearCity = {
                            viewModel.action(
                                SearchUiAction.ClearCity
                            )
                        },
                        clearCategory = {
                            viewModel.action(
                                SearchUiAction.ClearCategory
                            )
                        }
                    )

                    val filterApplied = state.appliedFilter.hasFilter()

                    when {
                        showDataNotFound -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LabelBackground(
                                    icon = R.drawable.folder_x,
                                    title = stringResource(R.string.data_not_found)
                                )
                            }

                        }


                        filterApplied && state.hasSearched -> {
                            // ✅ Ada data -> show LazyColumn
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxSize(),
                                contentPadding = PaddingValues(
                                    top = 14.dp
                                )
                            ) {

                                items(
                                    count = lazyPagingItems.itemCount,
                                    key = lazyPagingItems.itemKey {
                                        it.idProject
                                    }
                                ) { index ->
                                    val record = lazyPagingItems[index]
                                    record?.let { project ->
                                        val projectUi =
                                            project.toProjectUiItem(
                                                index + 1,
                                                state.appliedFilter.isFavorite
                                            )
                                        val isFavorite = state.favorites.any { fav -> fav.idProject == project.idProject.toInt() }

                                        ProjectCard(
                                            project = projectUi,
                                            onClick = { onNavigateToDetail(projectUi.idProject.toString()) },
                                            isFavorite = isFavorite,
                                            onToggleFavorite = { favorite ->
                                                viewModel.action(
                                                    SearchUiAction.ToggleFavorite(favorite)
                                                )
                                            }
                                        )
                                    }
                                }

                                lazyPagingItems.apply {
                                    when {
                                        loadState.refresh is LoadState.Loading -> {
                                            item {
                                                Box(
                                                    modifier = Modifier
                                                        .fillParentMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) { CircularProgressIndicator() }
                                            }
                                        }

                                        loadState.append is LoadState.Loading -> {
                                            item {
                                                CircularProgressIndicator(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(16.dp)
                                                        .wrapContentSize(Alignment.Center)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        else -> {

                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                LabelBackground(
                                    icon = R.drawable.funnel_plus,
                                    title = stringResource(R.string.anjuran_cari)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}


/**
 * Get first LoadState.Error from refresh/append/prepend
 */

private fun LazyPagingItems<*>.firstError(): LoadState.Error? {
    return listOf(
        loadState.refresh,
        loadState.append,
        loadState.prepend
    ).firstNotNullOfOrNull { it as? LoadState.Error }
}

/**
 * Custom styled snackbar host with rounded corners and themed colors
 *
 * @param snackBarHostState State holder for snackbar
 */

@Composable
private fun CustomSnackBarHost(snackBarHostState: SnackbarHostState) {
    SnackbarHost(
        hostState = snackBarHostState,
        // Add horizontal padding to snackbar
        modifier = Modifier.padding(horizontal = 8.dp)
    ) { data ->
        // Custom styled snackbar with rounded corners
        Snackbar(
            snackbarData = data,
            shape = RoundedCornerShape(12.dp), // Rounded corners
            containerColor = MaterialTheme.colorScheme.surfaceVariant, // Background color
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant // Text color
        )
    }
}

/**
 * Loading screen displayed during initialization
 */

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Center the loading indicator
    ) {
        CircularProgressIndicator() // Material loading spinner
    }
}










