package com.example.core_ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.AppTheme
import com.example.domain.model.StatisticProvince
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TopProvinceCard(
    modifier: Modifier = Modifier,
    provinces: List<StatisticProvince>,
    isShowAll: Boolean,
    onSeeAllClick: () -> Unit
) {
    val maxValue = remember(provinces) {
        provinces.maxOfOrNull { it.total } ?: 1
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20   .dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = "Top 5 Provinsi",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(10.dp))

            provinces.forEach { item ->
                ProvinceProgressItem(
                    name = item.province,
                    value = item.total,
                    progress = item.total.toFloat() / maxValue
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onSeeAllClick),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = if (isShowAll) "Collapse" else "Show All" ,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.size(10.dp))

                Icon(
                    imageVector = if (isShowAll) {
                        Icons.Default.ExpandLess
                    } else {
                        Icons.Default.ExpandMore
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun ProvinceProgressItem(
    name: String,
    value: Int,
    progress: Float
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .clip(RoundedCornerShape(50)),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCard() {
    AppTheme {
        val sampleProvinces = persistentListOf(
            StatisticProvince(province = "Jawa Barat", total = 2450),
            StatisticProvince(province = "Jawa Timur", total = 2100),
            StatisticProvince(province = "Jawa Tengah", total = 1890),
            StatisticProvince(province = "DKI Jakarta", total = 1650),
            StatisticProvince(province = "Banten", total = 1200)
        )

        TopProvinceCard(
            modifier = Modifier.padding(16.dp),
            provinces = sampleProvinces,
            onSeeAllClick = { /* Handle see all click */ },
            isShowAll = false
        )
    }
}