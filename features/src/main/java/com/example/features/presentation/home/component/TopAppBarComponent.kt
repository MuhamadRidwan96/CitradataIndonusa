package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.NotificationWithBadge
import com.example.features.presentation.profile.screen.subscreen.update.ProfileHeader

@Composable
fun TopAppBarContent(
    hello: String,
    name: String,
    count: Int,
    onClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp, end = 16.dp)
            .heightIn(min = 65.dp, max  = 95.dp)
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        ProfileHeader(
            name = name,
            hello = hello)

        NotificationWithBadge(
            count = count,
            onClick = onClick
        )
    }
}
