package com.example.features.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_ui.R
import com.example.data.utils.Constant
import com.example.data.utils.DataNotFoundException
import com.example.data.utils.TokenExpiredException
import com.example.features.presentation.home.component.ErrorBottomSheet
import com.example.features.presentation.home.component.PagingErrorItem
import com.example.features.presentation.home.component.ProjectCard
import com.example.features.presentation.home.state.toDataState
import com.example.features.presentation.search.component.ChipsRow
import com.example.features.presentation.search.component.LabelBackground
import com.example.features.presentation.search.component.SearchBottomSheet
import com.example.features.presentation.search.component.SearchScreenMain
import com.example.features.presentation.search.state.hasFilter
import com.example.features.presentation.search.viewmodel.CityViewModel
import com.example.features.presentation.search.viewmodel.DataEvent
import com.example.features.presentation.search.viewmodel.ProvinceViewModel
import com.example.features.presentation.search.viewmodel.SearchViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    provinceVM: ProvinceViewModel = hiltViewModel(),
    cityVM: CityViewModel = hiltViewModel(),
    onNavigateToLogin: () -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onNavigateToDetail: (String) -> Unit
) {
    val searchState by viewModel.appliedState.collectAsState()
    var query by remember { mutableStateOf("") }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState =
        rememberModalBottomSheetState(skipPartiallyExpanded = true, confirmValueChange = { true })
    val lazyPagingItems = viewModel.dataPaging.collectAsLazyPagingItems()
    val favorites by viewModel.favorite.collectAsState()
    val hasSearch by viewModel.hasSearched.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val sheet = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }
    var showDataNotFound by remember { mutableStateOf(false) }

    LaunchedEffect(lazyPagingItems.loadState) {
        val refresh = lazyPagingItems.loadState.refresh
        val append = lazyPagingItems.loadState.append
        val prepend = lazyPagingItems.loadState.prepend

        val error =
            listOf(refresh, append, prepend).find { it is LoadState.Error } as? LoadState.Error
        if (error != null) {
            when (error.error) {
                is TokenExpiredException -> {
                    showSheet = true
                }

                is DataNotFoundException -> {
                    showDataNotFound = true
                }

                else -> {
                    snackBarHostState.showSnackbar(error.error.message ?: "Unexpected error")
                }
            }
        } else {
            showDataNotFound = false
        }
    }

    if (!searchState.hasFilter() || !hasSearch) {
        showDataNotFound = false
    }

    if (showSheet) {
        ErrorBottomSheet(
            message = stringResource(R.string.end_session),
            onDismiss = {
                coroutineScope.launch {
                    sheet.hide()
                    showSheet = false

                    onNavigateToLogin()
                    viewModel.onLogoutClicked()
                }
            },
            sheetState = sheet
        )
    }


    LaunchedEffect(Unit) {
        viewModel.dataEvent.collect { event ->
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
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.searh_disc),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.height(85.dp)
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) { data ->
                Snackbar(
                    snackbarData = data,
                    shape = RoundedCornerShape(12.dp),
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )

            }
        },
    ) { paddingValues ->

        if (showBottomSheet) {
            SearchBottomSheet(
                onDismiss = { showBottomSheet = false },
                sheetState = sheetState,
                viewModel = viewModel,
                provinceVM = provinceVM,
                cityVM = cityVM,
            )
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SearchScreenMain(
                query = query,
                onQueryChange = {projectName ->
                    query = projectName
                    viewModel.updateDraft { it.copy(projectName = projectName) }
                    viewModel.applyFilters()
                },
                onBottomSheet = { showBottomSheet = true },
            )

            ChipsRow(
                searchState = searchState,
                viewModel = viewModel,
                cityVM = cityVM,
                provinceVM = provinceVM
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
                    // ✅ Ada data -> tampilkan LazyColumn
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
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

                                loadState.refresh is LoadState.Error -> {
                                    val e = (loadState.refresh as LoadState.Error).error
                                    if (e !is TokenExpiredException) {
                                        coroutineScope.launch {
                                            snackBarHostState.showSnackbar(
                                                e.message ?: Constant.FAILED_PARSE
                                            )
                                        }
                                    }
                                }

                                loadState.append is LoadState.Error -> {
                                    item {
                                        PagingErrorItem("Tidak ada data berikutnya!")
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
















