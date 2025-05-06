package com.example.features.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.features.nav.FloatingBottomNavigationWithIndicator
import com.example.features.nav.graph.HomeNavGraph


@Composable
fun MainScreen(navController: NavHostController) {
    val innerNavController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {

        HomeNavGraph(
            navController = innerNavController,
            rootNavController = navController,
            modifier = Modifier.fillMaxSize()
        )

        FloatingBottomNavigationWithIndicator(
            navController = innerNavController,
            modifier = Modifier.align(
                Alignment.BottomCenter
            )
        )
    }
}
