package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.Shapes

@Composable
fun FilterCategoryRow(
    categories: List<FilterCategory>,
    selectedCategoryProjectId: Int?,
    onCategoryProjectSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.SpaceBetween,
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(categories) { category ->
            val isSelected = category.id == selectedCategoryProjectId
            Box(
                modifier = Modifier
                    .size(65.dp)
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = Shapes.large
                    ),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable { onCategoryProjectSelected(category.id) }
                        .padding(4.dp),
                ) {

                    Icon(
                        imageVector = category.icon,
                        contentDescription = category.label,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = category.label,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis

                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .height(2.dp)
                                .width(28.dp)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                        )
                    } else {
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
    }
}


data class FilterCategory(
    val id:Int,
    val label: String,
    val code: String,
    val icon: ImageVector
)