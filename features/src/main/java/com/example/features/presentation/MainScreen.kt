package com.example.features.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.features.nav.FloatingBottomNavigationWithIndicator
import com.example.features.nav.graph.HomeNavGraph


@Composable
fun MainScreen(navController: NavHostController) {
    val innerNavController = rememberNavController()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Konten utama dengan padding bottom untuk memberi ruang navigasi
        Column(modifier = Modifier.fillMaxSize()) {
            HomeNavGraph(
                navController = innerNavController,
                rootNavController = navController,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(12.dp)
        ) {
            FloatingBottomNavigationWithIndicator(navController = innerNavController)
        }
    }
}
