package com.example.features.presentation.home.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.R
import com.example.core_ui.component.TopProvinceCard
import com.example.features.presentation.home.component.DashboardShimmer
import com.example.features.presentation.home.component.DonutChartScreen
import com.example.features.presentation.home.component.ErrorBottomSheet
import com.example.features.presentation.home.component.LazyRowCardStatistic
import com.example.features.presentation.home.component.LineChart
import com.example.features.presentation.home.component.NotificationWithBadge
import com.example.features.presentation.home.component.ProfileHeaders
import com.example.features.presentation.home.component.SearchSection
import com.example.features.presentation.home.screen.HomeViewModel
import com.example.features.presentation.home.state.dashboard.DashboardUiAction
import com.example.features.presentation.home.state.dashboard.DashboardUiEvent
import com.example.features.presentation.home.utils.HomeCallbacks
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kotlin.math.abs


@Suppress("EffectKeys")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeCallbacks: HomeCallbacks,

    onScrollChange: (Boolean) -> Unit,

    viewmodel: HomeViewModel = hiltViewModel(),

    ) {

    val state by viewmodel.uiState.collectAsStateWithLifecycle()
    val unread by viewmodel.unread.collectAsStateWithLifecycle()

    val snackBarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState()

    val coroutineScope = rememberCoroutineScope()
    var errorMessage by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    var showErrorSheet by remember { mutableStateOf(false) }
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }

    var showCharts by remember { mutableStateOf(false) }


    var visibleProvince = remember(
        state.byProvince,
        state.isShowAll
    ) {
        if (state.isShowAll) {
            state.byProvince
        } else {
            state.byProvince.take(5).toImmutableList()
        }

    }


    LaunchedEffect(Unit) {
        viewmodel.uiEvent.collect { event ->
            when (event) {
                is DashboardUiEvent.Error -> {
                    errorMessage = event.message
                    showErrorSheet = true
                }

                is DashboardUiEvent.SnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }

                is DashboardUiEvent.NavigateToNotification -> {
                    homeCallbacks.navigateToNotification()
                }

                is DashboardUiEvent.NavigateToProjectByCategory -> {

                }

                is DashboardUiEvent.NavigateToProjectByCity -> {}
                is DashboardUiEvent.NavigateToProjectByStatus -> {}
                is DashboardUiEvent.Logout -> {
                    homeCallbacks.navigateToLogout()
                }
            }

        }
    }


    //Detect direction scroll
    LaunchedEffect(listState, onScrollChange) {
        var lastOffset = 0
        val threshold = 10
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { offset ->
                val diff = offset - lastOffset
                if (abs(diff) > threshold) {
                    onScrollChange(diff > 0)
                    lastOffset = offset
                }
            }
    }

    LaunchedEffect(state.isInitialized) {
        if (state.isInitialized) {
            withFrameNanos { }
            showCharts = true
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
                }
            }
        )
    }


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
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {
                        ProfileHeaders()
                    },
                    actions = {
                        NotificationWithBadge(
                            modifier = Modifier.padding(end = 8.dp),
                            onClick = {
                                viewmodel.action(
                                    DashboardUiAction.OnNotificationClick
                                )
                            },
                            unreadCount = unread
                        )
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),

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

            if (!state.isInitialized) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    DashboardShimmer()
                }
            } else {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                    SearchSection(
                        query = "",
                        onQueryChange = { query ->

                            viewmodel.action(
                                DashboardUiAction.OnSearchQueryChanged(query)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(18.dp),
                        contentPadding = PaddingValues(
                            top = 14.dp,
                            bottom = paddingValues.calculateBottomPadding() + 16.dp
                        ),
                        modifier = Modifier
                            .padding(top = 0.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
                            .fillMaxSize()
                    ) {

                        item(contentType = "Statistic Card") {

                            if (showCharts) {
                                LazyRowCardStatistic(
                                    byCategory = state.byCategory,
                                    categoryTrend = state.categoryTrend
                                )
                            }
                        }

                        item(contentType = "Line Chart") {
                            if (showCharts) {
                                LineChart(trendIProject = state.dashboard)
                            }
                        }

                        item(contentType = "Donut Chart") {
                            if (showCharts) {
                                DonutChartScreen(
                                    modifier = Modifier.fillMaxWidth(),
                                    status = state.byStatus
                                )
                            }
                        }

                        item(contentType = "TopProvinceCard") {
                            if (showCharts) {
                                TopProvinceCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    provinces = visibleProvince,
                                    onSeeAllClick = { viewmodel.action(DashboardUiAction.OnToggleProvince) },
                                    isShowAll = state.isShowAll,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}




