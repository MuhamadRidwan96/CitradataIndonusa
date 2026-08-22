package com.example.features.presentation.home.screen.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.component.LoadingOverlay
import com.example.domain.model.NotificationModel
import com.example.features.presentation.authentication.screen.signup.component.MyTopAppBar
import com.example.features.presentation.home.screen.NotificationViewModel
import com.example.features.presentation.home.state.notification.NotificationUiAction
import com.example.features.presentation.home.state.notification.NotificationUiEvent


@Suppress("EffectKeys")
@Composable
fun NotificationScreen(
    onNavigateToProject: (String) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    viewModel: NotificationViewModel = hiltViewModel(),

    ) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val notification by viewModel.notification.collectAsStateWithLifecycle()

    val snackBar: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, onNavigateToProject) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is NotificationUiEvent.Error -> {
                    snackBar.showSnackbar(
                        message = event.message
                    )
                }

                is NotificationUiEvent.SnackBar -> {
                    snackBar.showSnackbar(
                        event.message
                    )
                }

                is NotificationUiEvent.NavigateToProject -> {
                    onNavigateToProject(event.projectId)

                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.action(NotificationUiAction.Refresh)
    }
    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            MyTopAppBar(
                onBackClick = { onBackClick() },
                text = "Back"
            )
        },
        content = { paddingValues ->

            Box(
                modifier = modifier
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.surface
                            )
                        )
                    )
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                LazyColumn {
                    items(
                        items = notification,
                        key = { it.id }
                    ) { notification ->
                        NotificationItem(
                            notification = notification,
                            /**
                             * User klik notification
                             */
                            onClick = {
                                viewModel.action(
                                    NotificationUiAction.MarkAsRead(
                                        id = notification.id,
                                        userId = notification.userId
                                    )
                                )

                                /**
                                 * Navigasi ke detail projeect
                                 * */

                                viewModel.action(
                                    NotificationUiAction.ClickProject(
                                        projectId = notification.projectId.toString()
                                    )
                                )

                            },
                            onDelete = {

                                viewModel.action(
                                    NotificationUiAction.DeleteNotification(
                                        id = notification.id,
                                        userId = notification.userId
                                    )
                                )

                            }
                        )
                    }
                }
                if (state.isLoading) {
                    LoadingOverlay()
                }
            }
        })
}


@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    notification: NotificationModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val backgroundColor = if (!notification.isRead) {
        MaterialTheme.colorScheme.surface
    } else {
        MaterialTheme.colorScheme.surfaceDim
    }


    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .background(backgroundColor)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 48.dp)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (!notification.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                        )
                    }

                    Text(
                        text = notification.title,
                        style = if (!notification.isRead)
                            MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        else
                            MaterialTheme.typography.titleSmall
                    )
                }

                Text(text = notification.body, style = MaterialTheme.typography.bodyMedium)

                Text(
                    text = notification.createdAt,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Box(modifier = Modifier.align(Alignment.TopEnd)) {
                IconButton(onClick = { expanded = true }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.width(IntrinsicSize.Min)
                ) {
                    DropdownMenuItem(
                        text = { Text("Hapus") },
                        onClick = {
                            expanded = false
                            onDelete()
                        })
                }
            }
        }
    }
}