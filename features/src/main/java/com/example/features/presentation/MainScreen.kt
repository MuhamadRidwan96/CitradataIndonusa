package com.example.features.presentation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.Shapes
import com.example.features.nav.BottomNavItem
import com.example.features.nav.graph.HomeNavGraph


@Composable
fun MainScreen() {
    val navController: NavHostController = rememberNavController()
    Scaffold(
        bottomBar = { FloatingBottomNavigationWithIndicator(navController = navController) }
    ) { paddingValues ->
        HomeNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun FloatingBottomNavigationWithIndicator(navController: NavHostController) {

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Favorite,
        BottomNavItem.Profile
    )
    // Update selectedItem berdasarkan route saat ini
    var selectedItem by remember { mutableIntStateOf(0) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val horizontalPadding = 24.dp
    val itemWidth = (screenWidth - horizontalPadding * 2) / items.size

    // Animasi posisi indikator
    val indicatorOffset by animateDpAsState(
        targetValue = itemWidth * selectedItem,
        label = "Indicator Offset Animation"
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .align(Alignment.BottomCenter)
                .padding(
                    start = horizontalPadding,
                    end = horizontalPadding,
                    bottom = 28.dp
                ),
            shape = Shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 12.dp,
            shadowElevation = 12.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Indikator dengan animasi
                Box(
                    modifier = Modifier
                        .offset(x = indicatorOffset)
                        .width(itemWidth)
                        .height(4.dp)
                        .align(Alignment.BottomStart)
                        .background(MaterialTheme.colorScheme.primary)
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { selectedItem = index }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selectedItem == index)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )

                            // Hanya tampilkan title jika item dipilih
                            if (selectedItem == index) {

                                Text(
                                    text = item.title,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}