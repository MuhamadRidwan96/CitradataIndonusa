package com.example.features.presentation.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.presentation.home.component.ProjectCard
import com.example.features.presentation.home.screen.DataEvent
import com.example.features.presentation.home.utils.toDataState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {

    val favorites by viewModel.favorites.collectAsStateWithLifecycle(initialValue = emptyList())

    LaunchedEffect(Unit) {
        viewModel.dataEvent.collect { event ->
            when (event) {
                is DataEvent.ShowSnackBar -> {
                    // Tampilkan snackbar
                    snackBarHostState.showSnackbar(event.message)
                }

                else -> {}
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bookmark Projects",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.height(85.dp),
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
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    ) { paddingValues ->
        if (favorites.isEmpty()) {
            EmptyFavoriteState()
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(
                    items = favorites,
                    key = { _, item -> item.idProject }) { index, favorite ->
                    // Konversi FavoriteProject ke
                    val no = index + 1
                    val dataState = favorite.toDataState(no)

                    ProjectCard(
                        project = dataState,
                        onClick = { onNavigateToDetail(dataState.idProject.toString()) },
                        isFavorite = true, // Selalu true karena ini screen favorite
                        onToggleFavorite = { fav ->
                            viewModel.toggleFavorite(fav)
                        }
                    )
                }
            }
        }
    }
}

