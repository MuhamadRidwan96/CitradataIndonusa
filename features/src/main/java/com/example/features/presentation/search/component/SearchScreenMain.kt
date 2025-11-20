package com.example.features.presentation.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.CompactSearchBar

@Composable
fun SearchScreenMain(
    query: String,
    onQueryChange: (String) -> Unit,
    onBottomSheet: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        CompactSearchBar(
            modifier = Modifier.weight(1f),
            query = query,
            onQueryChange = onQueryChange,
            onClear = { onQueryChange("") }
        )
        IconButton(
            onClick = { onBottomSheet() },
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .size(36.dp)
                .background(MaterialTheme.colorScheme.primary),
            enabled = true
        ) {
            Icon(
                painter = painterResource(R.drawable.sliders_horizontal),
                contentDescription = "Filter",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}