package com.example.features.presentation.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.core_ui.R
import com.example.core_ui.component.FilterCategory
import com.example.core_ui.component.FilterCategoryRow

@Composable
fun ProjectCategory(
    modifier: Modifier = Modifier,
    categorySelected: Int?,
    onCategorySelected: (Int, String) -> Unit

) {
    val listCategory = remember {
        listOf(
            FilterCategory(5, "Highrise & Commercial", "HRC", R.drawable.building_2),
            FilterCategory(6, "Middle Projects", "MDL", R.drawable.building),
            FilterCategory(7, "Lower Projects", "LOW", R.drawable.house),
            FilterCategory(8, "Industrial & Infrastructure", "IND", R.drawable.factory),
            FilterCategory(9, "Fitting Out & Interior", "FTO", R.drawable.armchair)
        )
    }
    FilterCategoryRow(
        categories = listCategory,
        selectedCategoryProjectId = categorySelected,
        onCategoryProjectSelected = { id, name ->
            onCategorySelected(id, name)
        },
        modifier = modifier.fillMaxWidth(),
        icon = R.drawable.building_2
    )

}