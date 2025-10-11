package com.example.features.presentation.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.core_ui.R
import com.example.core_ui.component.FilterCategory
import com.example.core_ui.component.FilterCategoryRow

@Composable
fun CategoryFilterSection(
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val categories = remember {
        listOf(
            FilterCategory(1, "All Categories", "ALL", R.drawable.command),
            FilterCategory(5, "Highrise & Commercial", "HRC",R.drawable.building_2 ),
            FilterCategory(6, "Middle Projects", "MDL",R.drawable.building),
            FilterCategory(7, "Lower Projects", "LOW", R.drawable.house),
            FilterCategory(8, "Industrial & Infrastructure", "IND", R.drawable.factory),
            FilterCategory(9, "Fitting Out & Interior", "FTO", R.drawable.armchair)
        )
    }

    FilterCategoryRow(
        categories = categories,
        selectedCategoryProjectId = selectedCategory,
        onCategoryProjectSelected = { categoryId,name->
            onCategorySelected(categoryId)
        },
        modifier = modifier.fillMaxWidth(),
        icon = R.drawable.chart_column_stacked
    )
}

fun getCategoryCode(categoryId: Int): String {
    return when (categoryId) {
        5 -> "5"
        6 -> "6"
        7 -> "7"
        8 -> "8"
        9 -> "9"
        1 -> ""
        else -> ""
    }
}

