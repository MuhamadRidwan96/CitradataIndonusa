package com.example.features.presentation.home.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.StatusChip
import com.example.domain.response.ProjectPpr
import com.example.features.presentation.home.state.DetailState
import com.example.features.presentation.home.utils.cleanInfo
import com.example.features.presentation.home.utils.formatToFullDate

@Composable
fun ProgressProjectComponent(
    modifier: Modifier = Modifier,
    status: DetailState
) {
    val statusProgress = status.ppr
    Column(modifier = modifier.padding(16.dp)) {
        TitleSection(
            R.drawable.ic_schedule,
            stringResource(R.string.report_progress)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            statusProgress.forEach { item ->
                ProgressReportCard(item)
            }
        }
    }
}


@Composable
private fun ProgressReportCard(project: ProjectPpr) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
                    .background(Color(0xFF0066FF))
            )

            // Content
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = project.pprCode,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                    StatusChip(status = project.categoryName)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = formatToFullDate(project.pprCreated),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = project.newInfo.cleanInfo(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
