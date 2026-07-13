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
import com.example.features.presentation.home.screen.DataEvent
import com.example.features.presentation.home.screen.HomeViewModel
import com.example.features.presentation.home.state.HomeNavigation
import com.example.features.presentation.home.state.HomeUiEvent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import kotlin.math.abs


@Suppress("EffectKeys")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeNavigation: HomeNavigation,

    onScrollChange: (Boolean) -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },

    homeVm: HomeViewModel = hiltViewModel(),


    ) {

    val statistic by homeVm.statisticState.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var showErrorSheet by remember { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }


    val isInitialized = statistic.isLoaded


  /*  LaunchedEffect(pagingItems.loadState) {
        val error = pagingItems.loadState.refresh as? LoadState.Error
        if (error?.error is TokenExpiredException) {
            showErrorSheet = true
        }
    }*/

    LaunchedEffect(Unit) {
        homeVm.uiEvent.collect { event ->
            when (event) {
                HomeUiEvent.LogoutSuccess -> {
                    homeNavigation.toLogout()
                }
            }
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
                    homeVm.onLogoutClicked()
                }
            }
        )
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


    // Listen to UI Events
    LaunchedEffect(Unit) {
        homeVm.dataEvent.collect { event ->
            when (event) {
                is DataEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }

                is DataEvent.Success -> {}
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 1f),
                        MaterialTheme.colorScheme.surface
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
                            onClick = { homeNavigation.toNotification() }
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


            if (!isInitialized) {
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
                        query = searchQuery,
                        onQueryChange = {
                            searchQuery = it
                          /*  homeVm.applyProjectName(
                                mapOf("project_name" to it)
                            )*/
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(
                            bottom = 0.dp
                        ),
                        modifier = Modifier
                            .padding(top = 14.dp , bottom = 0.dp, start = 14.dp, end = 14.dp)
                            .fillMaxSize()

                    ) {

                        item(contentType = "Statistic Card") {
                            LazyRowCardStatistic(statistic = statistic)
                        }

                        item(contentType = "Line Chart") {
                            LineChart(trendIProject = statistic.dashboard)
                        }

                        item(contentType = "Donut Chart") {
                            DonutChartScreen(
                                modifier = Modifier.fillMaxWidth(),
                                status = statistic.byStatus
                            )
                        }

                        item(contentType = "TopProvinceCard") {
                            TopProvinceCard(
                                modifier = Modifier.fillMaxWidth(),
                                provinces = if (statistic.isShowAll) {
                                    statistic.byProvince
                                } else {
                                   statistic.byProvince
                                            .take(5)
                                        .toImmutableList()

                                },
                                onSeeAllClick = homeVm::toggleProvince,
                                isShowAll = statistic.isShowAll,
                            )
                        }
                    }
                }
            }
        }
    }
}


/* item(contentType = "Latest") {
     TextTitle(
         icon = R.drawable.fire,
         title = stringResource(R.string.latest),
         desc = ""
     )

     Spacer(modifier = Modifier.height(16.dp))
 }*/

/*items(
    count = pagingItems.itemCount,
    key = { index -> pagingItems[index]?.idProject ?: index },
    contentType = { "Data" }) { index: Int ->
    val recordData = pagingItems[index]
    recordData?.let {
        val no = index + 1
        val dataState = it.toDataState(uiState.isFavorite, no)
        val isFav = favorites.any { fav -> fav.idProject == it.idProject.toInt() }

        ProjectCard(
            project = dataState,
            onClick = { homeNavigation.toDetail(dataState.idProject.toString()) },
            isFavorite = isFav,
            onToggleFavorite = { favEntity ->
                homeVm.toggleFavorite(favEntity)
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}*/
/*  pagingItems.apply {
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
                      snackBarHostState.showSnack bar(
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
  }*/





