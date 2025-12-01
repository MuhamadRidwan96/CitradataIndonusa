package com.example.features.presentation.authentication.screen.signup.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    onBackClick: () -> Unit,
    text: String
) {

    val navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }
    }

    val actions: @Composable RowScope.() -> Unit = {
        IconButton(onClick = { /* Handle attach */ }) {
            Icon(imageVector = Icons.Default.AttachFile, contentDescription = "Attach")
        }
        IconButton(onClick = { /* Handle calendar */ }) {
            Icon(imageVector = Icons.Default.Event, contentDescription = "Calendar")
        }
        IconButton(onClick = { /* Handle more */ }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
        }
    }

    TopAppBar(
        title = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = navigationIcon,
        actions = actions
    )
}