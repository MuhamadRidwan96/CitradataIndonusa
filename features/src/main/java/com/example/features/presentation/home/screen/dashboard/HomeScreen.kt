package com.example.features.presentation.home.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.core_ui.R
import com.example.core_ui.component.TextTitle
import com.example.data.utils.TokenExpiredException
import com.example.features.presentation.home.component.CarouselDummy
import com.example.features.presentation.home.component.DashboardShimmer
import com.example.features.presentation.home.component.ErrorBottomSheet
import com.example.features.presentation.home.component.PagingErrorItem
import com.example.features.presentation.home.component.ProjectCard
import com.example.features.presentation.home.component.SearchSection
import com.example.features.presentation.home.component.StatisticScreen
import com.example.features.presentation.home.component.TopAppBarContent
import com.example.features.presentation.home.screen.DataEvent
import com.example.features.presentation.home.screen.HomeViewModel
import com.example.features.presentation.home.screen.NotificationViewModel
import com.example.features.presentation.home.screen.StatisticViewModel
import com.example.features.presentation.home.state.toDataState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewmodel: HomeViewModel = hiltViewModel(),
    notificationViewModel: NotificationViewModel = hiltViewModel(),
    statisticViewModel: StatisticViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onNavigateToLogin: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToNotification: () -> Unit,
    onScrollChange: (Boolean) -> Unit
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    val pagingItems = viewmodel.currentPagingData.collectAsLazyPagingItems()
    val profile = viewmodel.userProfile

    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showErrorSheet by remember { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    val favorites by viewmodel.favoriteProjects.collectAsState()
    val count by notificationViewModel.unreadCount.collectAsState()
    val isInitialized by viewmodel.isInitialized.collectAsStateWithLifecycle()
    val statisticState by statisticViewModel.statisticState.collectAsStateWithLifecycle()

    LaunchedEffect(pagingItems.loadState) {
        val error = pagingItems.loadState.refresh as? LoadState.Error
        if (error?.error is TokenExpiredException) {
            showErrorSheet = true
        }
    }

    //Run Statistic
    LaunchedEffect(Unit) {
        statisticViewModel.fetchStatistic()
    }

    LaunchedEffect(statisticState.isLoaded) {
        if (statisticState.isLoaded) {
            viewmodel.refreshPaging()
        }

    }

    if (showErrorSheet) {
        ErrorBottomSheet(
            message = stringResource(R.string.end_session),
            sheetState = sheetState,
            onDismiss = {
                coroutineScope.launch {
                    sheetState.hide()
                    showErrorSheet = false
                    onNavigateToLogin()
                    viewmodel.onLogoutClicked()
                }
            }
        )
    }

    //Detect direction scroll
    LaunchedEffect(listState) {
        var lastOffset = 0
        val threshold = 10
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { offset ->
                val diff = offset - lastOffset
                if (kotlin.math.abs(diff) > threshold) {
                    onScrollChange(diff > 0)
                    lastOffset = offset
                }
            }
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
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarContent(
                        name = profile?.name ?: "",
                        hello = stringResource(R.string.hello),
                        count = count,
                        onClick = { onNavigateToNotification() },
                    )
                },
                scrollBehavior = scrollBehavior,

                )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
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

        if (!isInitialized) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                DashboardShimmer()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                SearchSection(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                        viewmodel.applyProjectName(
                            mapOf("project_name" to it)
                        )
                    },
                    modifier = Modifier
                        .padding(top = 6.dp, bottom = 16.dp)
                        .fillMaxWidth()
                )

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        bottom = paddingValues.calculateBottomPadding() + 50.dp,
                    ),
                    modifier = Modifier.fillMaxSize()

                ) {
                    item(contentType = "Carousel") {
                        CarouselDummy()
                    }

                    item(contentType = "Statistic") {
                        TextTitle(
                            icon = R.drawable.chart_column_stacked,
                            title = stringResource(R.string.statistic)
                        )
                    }

                    item(contentType = "Statistic") {
                        StatisticScreen(
                            viewModel = statisticViewModel
                        )
                    }

                    item(contentType = "Latest") {
                        TextTitle(
                            icon = R.drawable.fire,
                            title = stringResource(R.string.latest)
                        )
                    }

                    items(
                        count = pagingItems.itemCount,
                        key = { index -> pagingItems[index]?.idProject ?: index },
                        contentType = { "Data" }) { index: Int ->
                        val recordData = pagingItems[index]
                        recordData?.let {
                            val no = index + 1
                            val dataState = it.toDataState(uiState.isFavorite, no)
                            val isFav =
                                favorites.any { fav -> fav.idProject == it.idProject.toInt() }
                            ProjectCard(
                                project = dataState,
                                onClick = { onNavigateToDetail(dataState.idProject.toString()) },
                                isFavorite = isFav,
                                onToggleFavorite = { favEntity ->
                                    viewmodel.toggleFavorite(favEntity)
                                },
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                    pagingItems.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                //    item { LoadingItem() }
                            }

                            loadState.append is LoadState.Loading -> {
                                //  item { LoadingItem() }
                            }

                            loadState.refresh is LoadState.Error -> {
                                val e = (loadState.refresh as LoadState.Error).error
                                if (e !is TokenExpiredException) {
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(
                                            e.message ?: "Gagal memuat data!"
                                        )
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
                /*  if (pagingItems.loadState.refresh is LoadState.Loading) {
                     // LoadingItem()
                  }*/
            }
        }
    }
}





