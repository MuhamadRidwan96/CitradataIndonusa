package com.example.features.presentation.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        val colorScheme = MaterialTheme.colorScheme
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) colorScheme.primary else colorScheme.outlineVariant
        )
    }
}