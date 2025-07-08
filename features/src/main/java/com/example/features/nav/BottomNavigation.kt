package com.example.features.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.features.nav.graph.Graph


@Composable
fun FloatingBottomNavigationWithIndicator(
    navController: NavHostController
) {

    val items = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Search,
            BottomNavItem.Favorite,
            BottomNavItem.Profile
        )
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 16.dp,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
            ) {
           MainBottomNavigation(items, navController)
        }
    }
}


@Composable
fun MainBottomNavigation(
    items: List<BottomNavItem>,
    navController: NavHostController
) {

    val currentDestination by navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        windowInsets = WindowInsets(0)
    ) {
        items.forEach { navigate ->
            val selected = currentRoute == navigate.route
            NavigationBarItem(
                selected = selected,
                icon = {
                    Icon(
                        navigate.icon,
                        contentDescription = navigate.title,
                        tint = if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    )
                },
                label = {
                    Text(
                        navigate.title,
                        color = if (selected) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.outline
                    )
                },
                onClick = {
                    if (currentRoute != navigate.route) {
                        navController.navigate(navigate.route) {
                            popUpTo(Graph.HOME) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                )
            )
        }
    }
}
