package com.example.features.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R
import com.example.features.nav.destination.Screen


@Composable
fun MainBottomNavigation(
    modifier: Modifier = Modifier,
    currentDestination: String?,
    onDestinationSelect: (String) -> Unit
) {
    val bottomItems = listOf(
        BottomNavItem(Screen.Home.route, "Home", R.drawable.house),
        BottomNavItem(Screen.Search.route,"Search",R.drawable.compass),
        BottomNavItem(Screen.Favorite.route,"Favorite",R.drawable.folder_heart),
        BottomNavItem(Screen.Profile.route,"Profile",R.drawable.hard_hat)
    )

    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shadowElevation = 3.dp,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp, // 🔑 Hilangkan karena sudah ada di Surface
            modifier = Modifier.height(65.dp),
            windowInsets = WindowInsets(0.dp)// 🔑 Kembali ke default
        ) {
            bottomItems.forEach { navigate ->
                val selected = currentDestination == navigate.destination
                NavigationBarItem(
                    selected = selected,
                    icon = {
                        Icon(
                            painter = painterResource(navigate.icon),
                            contentDescription = navigate.title,
                            modifier = Modifier.size(if (selected) 22.dp else 18.dp)
                                .animateContentSize(),
                            tint = if (selected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    },
                 label = {
                     AnimatedVisibility(
                         visible = selected,
                         enter = fadeIn() + expandHorizontally(),
                         exit = fadeOut() + shrinkHorizontally()
                     ) {
                         Text(
                             navigate.title,
                             color = if (selected) MaterialTheme.colorScheme.primary
                             else MaterialTheme.colorScheme.onSurface,
                             style = MaterialTheme.typography.labelSmall,
                             fontSize = 10.sp
                         )
                     }

                    },
                    onClick = {
                        if(!selected){
                            onDestinationSelect(navigate.destination)
                        }
                    },
                    alwaysShowLabel = false ,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}



