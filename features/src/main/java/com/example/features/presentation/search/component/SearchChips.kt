package com.example.features.presentation.search.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SearchChips(
    selectedCategoryId: Int?,
    onCategorySelect: (Int?, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val buildingCategories = remember {

        listOf(
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
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier
            .fillMaxWidth(),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        items(items = buildingCategories, key = {it.id}) { category ->
            CategoryChip(
                category = category,
                isSelected = category.id == selectedCategoryId,
                onClick = { onCategorySelect(category.id, category.name) }
            )
        }
    }
}

@Composable
fun CategoryChip(modifier  : Modifier = Modifier, category: BuildingCategory, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(25),
        tonalElevation = 2.dp,
        shadowElevation = 1.dp,
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface),
        modifier = modifier.padding(4.dp)
            .height(42.dp),

    ) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                text = category.name,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp).fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

data class BuildingCategory(
    val id: Int,
    val name: String
)

