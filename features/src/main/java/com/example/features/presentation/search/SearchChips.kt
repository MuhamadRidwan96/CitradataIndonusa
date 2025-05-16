package com.example.features.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchChips(
    selectedCategoryId: Int?,
    onCategorySelected: (Int) -> Unit
) {
    val buildingCategories by produceState(initialValue = emptyList()) {

        value = listOf(
            BuildingCategory(5, "Mix Use"),
            BuildingCategory(6, "Hotel"),
            BuildingCategory(7, "Apartment"),
            BuildingCategory(8, "Office"),
            BuildingCategory(9, "Shopping Center"),
            BuildingCategory(10, "Shop House"),
            BuildingCategory(11, "Education"),
            BuildingCategory(12, "Community"),
            BuildingCategory(13, "Health"),
            BuildingCategory(14, "Real Estate"),
            BuildingCategory(15, "Residential"),
            BuildingCategory(16, "Recreation"),
            BuildingCategory(17, "Factory"),
            BuildingCategory(18, "Infrastructure"),
            BuildingCategory(19, "Oil & Gas"),
            BuildingCategory(20, "Mining")
        )
    }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buildingCategories.forEach { category ->
            val isSelected = selectedCategoryId == category.id
            FilterChip(
                selected = isSelected,
                onClick = {
                    onCategorySelected(category.id)
                },
                modifier = Modifier.padding(4.dp),
                shape = RoundedCornerShape(6.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    selectedLabelColor = MaterialTheme.colorScheme.onSurface
                ),
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
                label = { Text(category.name) }
            )
        }
    }
}

data class BuildingCategory(
    val id: Int,
    val name: String
)

