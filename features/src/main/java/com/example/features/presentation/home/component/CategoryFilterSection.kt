package com.example.features.presentation.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.House
import androidx.compose.material.icons.filled.Store
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.core_ui.component.FilterCategory
import com.example.core_ui.component.FilterCategoryRow

@Composable
fun CategoryFilterSection(
    selectedCategory: Int,
    onCategorySelected: (Int) -> Unit
) {
    val categories = remember {
        listOf(
            FilterCategory(5, "Highrise & Commercial", "HRC", Icons.Default.Apartment),
            FilterCategory(6, "Middle Project", "MDL", Icons.Default.Home),
            FilterCategory(7, "Low Project", "LOW", Icons.Default.House),
            FilterCategory(8, "Industrial & Infrastructure", "IND", Icons.Default.Factory),
            FilterCategory(9, "Fitting Out & Interior", "FTO", Icons.Default.Store)
        )
    }

    FilterCategoryRow(
        categories = categories,
        selectedCategoryProjectId = selectedCategory,
        onCategoryProjectSelected = { categoryId ->
            onCategorySelected(categoryId)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

fun getCategoryCode(categoryId: Int): String {
    return when (categoryId) {
        5 -> "5"
        6 -> "6"
        7 -> "7"
        8 -> "8"
        9 -> "9"
        else -> ""
    }
}