package com.example.features.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_ui.R
import com.example.data.utils.DataNotFoundException
import com.example.data.utils.TokenExpiredException
import com.example.features.presentation.home.component.ErrorBottomSheet
import com.example.features.presentation.home.component.ProjectCard
import com.example.features.presentation.home.state.toDataState
import com.example.features.presentation.search.component.ChipsRow
import com.example.features.presentation.search.component.LabelBackground
import com.example.features.presentation.search.component.SearchBottomSheet
import com.example.features.presentation.search.component.SearchScreenMain
import com.example.features.presentation.search.state.SearchBottomSheetAction
import com.example.features.presentation.search.state.hasFilter
import com.example.features.presentation.search.viewmodel.DataEvent
import com.example.features.presentation.search.viewmodel.LocationViewModel
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
 * @param locationViewModel ViewModel for location-related operations (provinces, cities)
 */

@Suppress("EffectKeys")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onNavigateToDetail: (String) -> Unit,

    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    viewModel: SearchViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),

    ) {
    // ========== State Management ==========

    // Collect the applied search filters state from ViewModel
    val searchState by viewModel.appliedState.collectAsState()

    // Collect the draft state for filters being edited in bottom sheet
    val draftState by viewModel.draftState.collectAsState()

    // Collect location state (provinces and cities data)
    val locationState by locationViewModel.locationState.collectAsState()

    // Collect list of favorite projects from ViewModel
    val favorites by viewModel.favorite.collectAsState()

    // Track whether user has performed a search
    val hasSearch by viewModel.hasSearched.collectAsState()

    // Track whether initial data loading is complete
    val isInitialized by viewModel.isInitialized.collectAsStateWithLifecycle()

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
    var query by remember { mutableStateOf("") }

    val showDataNotFound by remember {
        derivedStateOf {
            val hasError = lazyPagingItems.loadState.refresh is LoadState.Error
            val errorIsNotFound = (lazyPagingItems.loadState.refresh as? LoadState.Error)?.error is DataNotFoundException

            hasError && errorIsNotFound
        }
    }

    // ========== Side Effects ==========

    /* -------------------- Paging Error Handling -------------------- */
    // Monitor load state changes and handle errors



    val loadState = lazyPagingItems.loadState
    LaunchedEffect(loadState) {
        // Find the first error from refresh, append, or prepend load states
        val error = lazyPagingItems.firstError()

        // Handle different error types
        if (error != null) {
            handlingPagingErrors(
                error = error,
                onTokenExpire = {
                    coroutineScope.launch {
                        if (filterSheetState.isVisible) {
                            filterSheetState.hide()
                            showFilterSheet = false
                        }
                        errorShowSheet = true
                    }

                },
                onDataNotFound = {
                },
                onGeneralError = { message ->
                    coroutineScope.launch {
                        snackBarHostState.showSnackbar(message)
                    }

                }
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

                    viewModel.onLogoutClicked()
                }

            },
            sheetState = sessionSheetState
        )
    }


    LaunchedEffect(Unit) {
        viewModel.dataEvent.collectLatest { event ->
            when (event) {
                is DataEvent.Success -> {}
                is DataEvent.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
            }

        }
    }

    /* -------------------- UI -------------------- */

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.searh_disc),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                modifier = Modifier.heightIn(min = 65.dp, max = 95.dp)
            )
        },
        snackbarHost = {
            CustomSnackBarHost(snackBarHostState = snackBarHostState)
        },
    ) { paddingValues ->
        if (!isInitialized) {
            LoadingScreen()
        } else {

            if (showFilterSheet) {
                val focusManager = LocalFocusManager.current
                val keyboardController = LocalSoftwareKeyboardController.current

                val onBottomSheetAction: (SearchBottomSheetAction) -> Unit = { action ->
                    when (action) {

                        is SearchBottomSheetAction.Apply -> {
                            // Clear focus and hide keyboard BEFORE closing sheet
                            focusManager.clearFocus()
                            keyboardController?.hide()

                            coroutineScope.launch {
                                delay(300)
                                filterSheetState.hide()
                                showFilterSheet = false
                            }
                            viewModel.onAction(action)
                        }

                        else -> viewModel.onAction(action)
                    }
                }
                SearchBottomSheet(
                    selectedCity = draftState.cityName,
                    selectedProvince = draftState.provinceName,
                    searchState = draftState,
                    locationState = locationState,
                    sheetState = filterSheetState,
                    onAction = onBottomSheetAction,
                    onGetProvince = { locationViewModel.getProvinces() },
                    onGetCity = { idProvinces ->
                        locationViewModel.getCity(idProvinces)
                    },
                    onDismiss = {
                        // Clear focus and hide keyboard when user swipes down
                        focusManager.clearFocus()
                        keyboardController?.hide()

                        coroutineScope.launch {
                            delay(150) // Wait for keyboard to hide
                            filterSheetState.hide()
                            showFilterSheet = false
                        }
                    }
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
                    query = query,
                    onQueryChange = { projectName ->
                        query = projectName
                        viewModel.updateDraft { it.copy(projectName = projectName) }
                        viewModel.applyFilters()
                    },
                    onBottomSheet = { showFilterSheet = true },
                )

                ChipsRow(
                    searchState = searchState,
                    clearPpr = viewModel::clearPpr,
                    clearDateRange = viewModel::clearDateRange,
                    clearStatus = viewModel::clearStatus,
                    clearBuilding = viewModel::clearBuilding,
                    clearProvince = {
                        locationViewModel.clearProvince()
                        viewModel.clearProvince()
                    },
                    clearCity = {
                        viewModel.clearCity()
                        locationViewModel.clearCity()
                    },
                    clearCategory = viewModel::clearCategory
                )

                val filterApplied = searchState.hasFilter()
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

                    filterApplied && hasSearch -> {
                        // ✅ Ada data -> show LazyColumn
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(
                                top = 14.dp
                            )
                        ) {
                            items(lazyPagingItems.itemCount) { index ->
                                val recordData = lazyPagingItems[index]
                                recordData?.let {
                                    val no = index + 1
                                    val state = it.toDataState(searchState.isFavorite, no)
                                    val fav =
                                        favorites.any { fav -> fav.idProject == it.idProject.toInt() }

                                    ProjectCard(
                                        project = state,
                                        onClick = { onNavigateToDetail(state.idProject.toString()) },
                                        isFavorite = fav,
                                        onToggleFavorite = { favEntity ->
                                            viewModel.toggleFavorite(favEntity)
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
 * Handles paging error states and updates UI accordingly
 *
 * @param error Callback load state error
 * @param onTokenExpire Callback when authentication token expires
 * @param onDataNotFound Callback to update data not found state
 * @param onGeneralError Callback when an error occurs with error message
 */

private fun handlingPagingErrors(
    error: LoadState.Error,
    onTokenExpire: () -> Unit,
    onDataNotFound: () -> Unit,
    onGeneralError: (String) -> Unit

) {
    when (error.error) {
        is TokenExpiredException -> onTokenExpire()
        is DataNotFoundException -> onDataNotFound()
        else -> onGeneralError(error.error.message ?: "Unexpected Error")

    }
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












