package com.example.features.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        NavigationBar{
            bottomItems.forEach { navigate ->
                val selected = currentDestination == navigate.destination
                NavigationBarItem(
                    selected = selected,
                    icon = {
                        Icon(
                            painter = painterResource(navigate.icon),
                            contentDescription = navigate.title,
                            modifier = Modifier.size(if (selected) 27.dp else 22.dp)
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
                             else MaterialTheme.colorScheme.primary,
                             style = MaterialTheme.typography.labelSmall,
                             fontSize = 12.sp
                         )
                     }

                    },
                    onClick = {
                        if(!selected){
                            onDestinationSelect(navigate.destination)
                        }
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}



