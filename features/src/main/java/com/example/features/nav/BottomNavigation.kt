package com.example.features.nav

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.features.nav.graph.Graph


@Composable
fun FloatingBottomNavigationWithIndicator(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val items = remember {
        listOf(
            BottomNavItem.Home,
            BottomNavItem.Search,
            BottomNavItem.Favorite,
            BottomNavItem.Profile
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedItem = items.indexOfFirst { it.route == currentRoute }.takeIf { it != -1 } ?: 0

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val horizontalPadding = 24.dp
    val itemWidth = (screenWidth - horizontalPadding * 2) / items.size

    val indicatorOffset by animateDpAsState(
        targetValue = itemWidth * selectedItem,
        label = "Indicator Offset"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 28.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = horizontalPadding),
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 12.dp,
            shadowElevation = 12.dp
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Indicator(indicatorOffset, itemWidth)
                NavigationItems(items, selectedItem, currentRoute, navController)
            }
        }
    }
}

@Composable
private fun BoxScope.Indicator(offset: Dp, width: Dp) {
    Box(
        modifier = Modifier
            .offset(x = offset)
            .width(width)
            .height(4.dp)
            .align(Alignment.BottomStart)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(50)
            )
    )
}

@Composable
private fun NavigationItems(
    items: List<BottomNavItem>,
    selectedIndex: Int,
    currentRoute: String?,
    navController: NavHostController
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            NavigationItem(
                item = item,
                isSelected = index == selectedIndex,
                onClick = {
                    if (item.route != currentRoute) {
                        navController.navigate(item.route) {
                            popUpTo(Graph.HOME) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun RowScope.NavigationItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
        )

        if (isSelected) {
            Text(
                text = item.title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


