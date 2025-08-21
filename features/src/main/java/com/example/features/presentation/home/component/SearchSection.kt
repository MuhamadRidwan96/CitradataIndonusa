package com.example.features.presentation.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.CompactSearchBar

@Composable
fun SearchSection(
    query: String,
    onQueryChange: (String) -> Unit
) {
    CompactSearchBar(
        query = query,
        onQueryChange = onQueryChange,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()

    )
}