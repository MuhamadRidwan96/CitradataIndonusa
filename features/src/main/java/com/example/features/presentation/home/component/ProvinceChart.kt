package com.example.features.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ProvinceChart(provinceData: Map<String, Int>) {

    val sorted = provinceData.entries.sortedByDescending { it.value }.take(7)
    Column(modifier = Modifier.fillMaxWidth()) {
        sorted.forEach { (province, count) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .fillMaxWidth(count / 200f)
                        .background(MaterialTheme.colorScheme.primary)
                        .clip(RoundedCornerShape(24.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "$province ($count)",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}