package com.example.features.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.features.nav.MainBottomNavigation
import com.example.features.nav.graph.HomeNavGraph


@Composable
fun MainScreen( modifier: Modifier = Modifier, navController: NavHostController, projectId: String? = null) {
    val innerNavController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = { MainBottomNavigation(innerNavController) },
        contentWindowInsets = WindowInsets(0.dp)
    )
    { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues)) {
            HomeNavGraph(
                navController = innerNavController,
                rootNavController = navController,
                projectId = projectId
            )
        }
    }
}


