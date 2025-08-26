package com.example.features.presentation.home.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.data.utils.TokenExpiredException
import com.example.feature_login.R
import com.example.features.presentation.home.screen.DataEvent
import com.example.features.presentation.home.screen.HomeViewModel
import com.example.features.presentation.home.component.CarouselDummy
import com.example.features.presentation.home.component.CategoryFilterSection
import com.example.features.presentation.home.component.ErrorBottomSheet
import com.example.features.presentation.home.component.LoadingItem
import com.example.features.presentation.home.component.PagingErrorItem
import com.example.features.presentation.home.component.ProjectCard
import com.example.features.presentation.home.component.SearchSection
import com.example.features.presentation.home.component.TopAppBarContent
import com.example.features.presentation.home.component.getCategoryCode
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
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val listState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val favorites by viewmodel.favoriteProjects.collectAsState()

    LaunchedEffect(pagingItems.loadState) {
        val error = pagingItems.loadState.refresh as? LoadState.Error
        if (error?.error is TokenExpiredException) {
            showErrorSheet = true
        }
    }

    if (showErrorSheet) {
        ErrorBottomSheet(
            message = stringResource(R.string.end_session),
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
            TopAppBar(
                title = {
                    TopAppBarContent(
                        imageVector = Icons.Default.Notifications,
                        photo = profile?.photo ?: "",
                        email = profile?.email ?: "",
                        name = profile?.name ?: ""
                    )
                },
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = {
            SnackbarHost(
                snackBarHostState,
                modifier = Modifier
                    .padding(bottom = 56.dp, start = 16.dp, end = 16.dp)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            SearchSection(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    viewmodel.applyProjectName(
                        mapOf("project_name" to it)
                    )
                },
                modifier = Modifier.padding(top = 12.dp)
            )

            LazyColumn(
                contentPadding = PaddingValues(
                    bottom = paddingValues.calculateBottomPadding() + 50.dp,
                    top = 12.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                state = listState

                ) {

                item {  CarouselDummy(modifier = Modifier.padding(bottom = 8.dp)) }

                item {
                    CategoryFilterSection(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { category ->
                            selectedCategory = category
                            viewmodel.applyCategories(getCategoryCode(category))
                        },
                        modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp, top = 8.dp)
                    )
                }

                items(pagingItems.itemCount) { index: Int ->
                    val recordData = pagingItems[index]
                    recordData?.let {
                        val no = index + 1
                        val dataState = it.toDataState(uiState.isFavorite, no)
                        val isFav = favorites.any{fav -> fav.idProject == it.idProject.toInt()}
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
                            item { LoadingItem() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingItem() }
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
            if (pagingItems.loadState.refresh is LoadState.Loading){
                LoadingItem()
            }
        }
    }
}







