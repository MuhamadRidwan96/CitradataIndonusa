package com.example.features.presentation.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.IconText
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.features.presentation.home.state.DataState
import com.example.features.presentation.home.utils.formatToFullDate
import com.example.features.presentation.home.utils.toFavoriteProjectEntity


@Composable
fun ProjectCard(
    project: DataState,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: (FavoriteProjectEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val cleanStat = cleanStatus(project.statProject ?: "")
    val cardConfiguration = rememberCardConfiguration(cleanStat)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = cardConfiguration.topPadding, bottom = 8.dp)
    ) {
        ProjectCardContent(
            project = project,
            onClick = onClick,
            isFavorite = isFavorite,
            onToggleFavorite = onToggleFavorite,
            cardConfiguration = cardConfiguration,
        )

        cardConfiguration.statusColor?.let { color ->
            StatusBadge(
                text = cleanStat,
                backgroundColor = color,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-8).dp, y = (-16).dp)
            )
        }
    }
}

@Composable
private fun ProjectCardContent(
    project: DataState,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: (FavoriteProjectEntity) -> Unit,
    cardConfiguration: CardConfiguration
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = cardConfiguration.border,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 4.dp,
                start = 12.dp,
                end = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            ProjectHeader(
                projectStatus = project.status ?: "",
                textCategory = project.category ?: "",
                number = project.no ?: 0
            )
            ProjectTitle(title = project.project ?: "")


            val dates = project.lastUpdate ?: ""
            ProjectMetadata(
                date = formatToFullDate(dates),
                location = project.location ?: "",
                province = project.province ?: "",
                idProject = project.idProject.toString()
            )

            HorizontalDivider(modifier = Modifier.height(0.5.dp))

            BottomCard(
                idRecord = project.idRecord ?: "",
                isFavorite = isFavorite,
                onFavoriteClick = { onToggleFavorite(project.toFavoriteProjectEntity()) }
            )
        }
    }
}

@Composable
fun BottomCard(
    modifier: Modifier = Modifier,
    idRecord: String,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconText(R.drawable.ic_contract, idRecord)
        FavoriteButton(
            isFavorite = isFavorite,
            onClick = onFavoriteClick
        )
    }
}



