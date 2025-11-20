package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.model.ProjectStatusCategoryModel
import com.example.feature_login.R

@Composable
fun ProjectStatusCategory(
    selectedStatusId: Int?,
    onStatusSelected: (Int? , String) -> Unit,    // Event ke parent
    modifier: Modifier = Modifier
) {
    val statuses by produceState(initialValue = emptyList()) {
        value = listOf(
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
        )
    }

    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(com.example.core_ui.R.drawable.funnel),
                contentDescription = "date",
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = stringResource(R.string.project_status),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
            )
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            statuses.forEach { category ->
                val isSelected = selectedStatusId == category.id
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        onStatusSelected(category.id, category.status)
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier
                        .heightIn(min = 42.dp)
                        .padding(4.dp),
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                    label = { Text(category.status) }
                )
            }
        }
    }
}




