package com.example.features.presentation.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R
import com.example.core_ui.component.IconText
import com.example.core_ui.component.StatusChip
import com.example.data.local.entity.FavoriteProjectEntity
import com.example.features.presentation.home.state.DataState
import com.example.features.presentation.home.utils.formatToFullDate
import com.example.features.presentation.home.utils.toFavoriteProjectEntity


@Composable
fun ProjectCard(
    project: DataState,
    onClick: () -> Unit,
    isFavorite : Boolean,
    onToggleFavorite : (FavoriteProjectEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val cleanStat = cleanStatus(project.statProject)
    val cardConfiguration = rememberCardConfiguration(cleanStat)



    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = cardConfiguration.topPadding, bottom = 12.dp)
    ) {
        ProjectCardContent(
            project = project,
            onClick = onClick,
            isFavorite = isFavorite,
            onToggleFavorite =onToggleFavorite,
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
    isFavorite : Boolean,
    onToggleFavorite : (FavoriteProjectEntity) -> Unit,
    cardConfiguration: CardConfiguration
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = cardConfiguration.border,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondary)
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
                projectStatus = project.status,
                textCategory = project.category.toString(),
                number = project.no
            )
            ProjectTitle(title = project.project)


            val dates = project.lastUpdate
            ProjectMetadata(
                date = formatToFullDate(dates),
                location = project.location,
                province = project.province,
                idProject = project.idProject.toString()
            )

            HorizontalDivider(modifier = Modifier.height(0.5.dp))

            BottomCard(
                idRecord = project.idRecord,
                isFavorite = isFavorite,
                onFavoriteClick = { onToggleFavorite(project.toFavoriteProjectEntity()) }
            )
        }
    }
}

@Immutable
private data class CardConfiguration(
    val topPadding: Dp,
    val border: BorderStroke?,
    val statusColor: Color?
)

@Composable
private fun rememberCardConfiguration(statProject: String): CardConfiguration {
    val status = cleanStatus(statProject)
    return remember(status) {
        when (status) {
            "New" -> CardConfiguration(
                topPadding = 12.dp,
                border = BorderStroke(
                    2.dp,
                    Color.Unspecified
                ),
                statusColor = Color.Unspecified
            )

            "Update" -> CardConfiguration(
                topPadding = 12.dp,
                border = BorderStroke(2.dp, Color.Unspecified),
                statusColor = Color.Unspecified
            )

            else -> CardConfiguration(
                topPadding = 0.dp,
                border = null,
                statusColor = null
            )
        }
    }.let { config ->
        // Resolve colors at composition time
        val colors = MaterialTheme.colorScheme
        config.copy(
            border = config.border?.copy(
                brush = SolidColor(
                    when (status) {
                        "New" -> colors.surfaceContainerHigh
                        "Update" -> colors.surfaceContainerHighest
                        else -> Color.Transparent
                    }
                )
            ),
            statusColor = when (status) {
                "New" -> colors.surfaceContainerHigh
                "Update" -> colors.surfaceContainerHighest
                else -> config.statusColor
            }
        )
    }
}

@Composable
private fun StatusBadge(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(38))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}

fun cleanStatus(raw:String): String{
    return raw
        .replace(Regex("<.*?>"), "")
        .trim()
}

@Composable
private fun ProjectHeader(
    projectStatus: String,
    textCategory: String,
    number: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HeaderText(number.toString())
            StatusChip(status = projectStatus)
            Spacer(modifier = Modifier.weight(1f))
            HeaderText(textCategory)
        }
    }
}

@Composable
private fun HeaderText(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 4.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}


@Composable
private fun ProjectTitle(modifier: Modifier = Modifier, title: String) {
    Column(modifier = modifier.padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 0.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ProjectMetadata(
    date: String,
    location: String,
    province: String,
    idProject: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        RowLocation(location, province)
        IconText(R.drawable.ic_calendar, date)
        TextComponent(text = stringResource(R.string.id_project)) {
            Text(
                text = idProject,
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium ,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun TextComponent(text: String, content: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = text, fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
        content()
    }
}

@Composable
private fun RowLocation(location: String, province: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_location),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(16.dp)
                .alignBy(FirstBaseline)

        )
        Text(
            text = "$location, $province",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
fun BottomCard(idRecord: String, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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


@Composable
private fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        val colorScheme = MaterialTheme.colorScheme
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Bookmark else Icons.Outlined.Bookmark,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) colorScheme.primary else colorScheme.outlineVariant
        )
    }
}





