package com.example.features.presentation.search.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ProjectStatusCategoryModel

@Composable
fun ProjectStatusCategory() {
    val statuses = remember {
        listOf(
            ProjectStatusCategoryModel(3, "Planning"),
            ProjectStatusCategoryModel(4, "Post Tender"),
            ProjectStatusCategoryModel(5, "Pilling Work"),
            ProjectStatusCategoryModel(6, "Construction Start"),
            ProjectStatusCategoryModel(7, "Under Construction"),
            ProjectStatusCategoryModel(8, "Existing"),
            ProjectStatusCategoryModel(9, "Hold Project"),
            ProjectStatusCategoryModel(10, "Project Canceled"),
            ProjectStatusCategoryModel(11, "Final Project"),
            ProjectStatusCategoryModel(12, "Finish"),
            ProjectStatusCategoryModel(-1, "Kosongkan pilihan")
        )
    }

    var selectedStatus by rememberSaveable { mutableStateOf<Int?>(null) }
    var showFullSheet by rememberSaveable { mutableStateOf(false) }

    Box {
        Column {
            StatusPreviewRow(
                statuses = statuses,
                selectedStatus = statuses.find { it.id == selectedStatus },
                onItemClick = { selectedStatus = it.id },
                onSeeAllClick = { showFullSheet = true }
            )
        }

        if (showFullSheet) {
            FullStatusSelectionSheet(
                statuses = statuses,
                selectedStatus = statuses.find { it.id == selectedStatus },
                onStatusSelected = { it ->
                    selectedStatus = it?.id
                    showFullSheet = false
                },
                onDismiss = { showFullSheet = false }
            )
        }
    }
}

@Composable
fun StatusPreviewRow(
    statuses: List<ProjectStatusCategoryModel>,
    selectedStatus: ProjectStatusCategoryModel?,
    onItemClick: (ProjectStatusCategoryModel) -> Unit,
    onSeeAllClick: () -> Unit
) {
    val visibleItems = remember(statuses) { statuses.take(4) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(visibleItems, key = { it.id }) { status ->
                StatusChip(
                    status = status,
                    isSelected = selectedStatus?.id == status.id,
                    onClick = { onItemClick(status) }
                )
            }
        }

        TextButton(
            onClick = onSeeAllClick, modifier = Modifier
                .padding(4.dp)
                .heightIn(min = 40.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("See All", fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun StatusChip(
    status: ProjectStatusCategoryModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val currentOnClick by rememberUpdatedState(onClick)

    Surface(
        shape = MaterialTheme.shapes.large,
        color = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.13f)
        else MaterialTheme.colorScheme.background,
        modifier = Modifier
            .clickable { currentOnClick() }
            .border(
                shape = MaterialTheme.shapes.large,
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
    ) {
        Text(
            text = status.status,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontSize = 11.sp,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurface
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullStatusSelectionSheet(
    statuses: List<ProjectStatusCategoryModel>,
    selectedStatus: ProjectStatusCategoryModel?,
    onStatusSelected: (ProjectStatusCategoryModel?) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        Column {
            Text(
                text = "Select Status",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn {
                items(statuses, key = { it.id }) { status ->
                    StatusSelectionRow(
                        status = status,
                        isSelected = selectedStatus?.id == status.id,
                        onClick = { onStatusSelected(status) }
                    )
                }
            }
        }
    }
}

@Composable
fun StatusSelectionRow(
    status: ProjectStatusCategoryModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = status.status,
            modifier = Modifier.weight(1f),
            fontSize = 13.sp
        )
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected"
            )
        }
    }
}



