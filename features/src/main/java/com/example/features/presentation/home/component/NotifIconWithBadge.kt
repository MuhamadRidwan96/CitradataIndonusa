package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.NotificationBadge

@Suppress("EffectKeys")
@Composable
fun NotificationWithBadge(
    unreadCount:Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier

) {

    Box(
        modifier = modifier
            .size(38.dp)
            .clickable { onClick() }
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,

        ) {
        Icon(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "Notification",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )

        if (unreadCount > 0) {
            NotificationBadge(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 4.dp, y = (-2).dp), // posisi di pojok kanan atas
            )
        }
    }
}