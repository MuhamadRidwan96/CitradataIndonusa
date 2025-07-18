package com.example.features.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.outlined.Folder
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.AppTheme
import com.example.core_ui.R
import com.example.features.presentation.home.state.DataState


@Composable
fun ProjectCard(
    project: DataState,
    onFavoriteClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val cardConfiguration = rememberCardConfiguration(project.statProject)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = cardConfiguration.topPadding, bottom = 8.dp,start = 12.dp, end = 12.dp)
    ) {
        ProjectCardContent(
            project = project,
            onFavoriteClick = onFavoriteClick,
            cardConfiguration = cardConfiguration
        )

        cardConfiguration.statusColor?.let { color ->
            StatusBadge(
                text = project.statProject,
                backgroundColor = color,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-12).dp, y = (-12).dp)
            )
        }
    }
}

@Composable
private fun ProjectCardContent(
    project: DataState,
    onFavoriteClick: (Boolean) -> Unit,
    cardConfiguration: CardConfiguration
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
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
                projectStatus = project.status,
                textCategory = project.category,
                number = project.no
            )

            ProjectTitle(
                title = project.project,
                idProject = project.idProject
            )

            ProjectMetadata(
                date = project.lastUpdate,
                location = project.location,
                province = project.province
            )

            HorizontalDivider(modifier = Modifier.height(0.5.dp))

            BottomCard(
                idRecord = project.idRecord,
                isFavorite = project.isFavorite,
                onFavoriteClick = { onFavoriteClick(false) }
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
    return remember(statProject) {
        when (statProject) {
            "New" -> CardConfiguration(
                topPadding = 12.dp,
                border = BorderStroke(2.dp, Color.Unspecified), // Will be resolved at composition time
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
        val colorScheme = MaterialTheme.colorScheme
        config.copy(
            border = config.border?.copy(
                brush = SolidColor(
                    when (statProject) {
                        "New" -> colorScheme.primary
                        "Update" -> colorScheme.scrim
                        else -> Color.Transparent
                    }
                )
            ),
            statusColor = when (statProject) {
                "New" -> colorScheme.primary
                "Update" -> colorScheme.scrim
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
        style = MaterialTheme.typography.labelMedium,
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
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
            .background(MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 4.dp),
        fontSize = 12.sp,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onBackground
    )
}


@Composable
private fun ProjectTitle(title: String, idProject: String) {
    Column(modifier = Modifier.padding(top = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = idProject,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun ProjectMetadata(
    date: String,
    location: String,
    province: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {


        IconText(R.drawable.ic_place_marker, location)
        IconText(R.drawable.ic_location, province)
        IconText(R.drawable.ic_calendar, date)
    }
}

@Composable
private fun IconText(
    @DrawableRes icon: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 0.dp, horizontal = 2.dp)
            .wrapContentHeight()
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun BottomCard(idRecord: String, isFavorite: Boolean,  onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = idRecord,
            fontSize = 12.sp,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        FavoriteButton(
            isFavorite = isFavorite,
            onClick = onFavoriteClick
        )

    }
}


@Immutable
private data class StatusChipColors(
    val backgroundColor: Color,
    val textColor: Color
)

@Composable
private fun StatusChip(status: String) {
    val chipColors = remember(status) {
        when (status.lowercase()) {
            "planning" -> StatusChipColors(Color(0xFF4CAF50), Color.White)
            "post tender", "pilling work" -> StatusChipColors(Color(0xFFFFEB3B), Color.Black)
            "construction start", "project canceled" -> StatusChipColors(Color(0xFFF44336), Color.White)
            "under construction", "final project" -> StatusChipColors(Color(0xFF2196F3), Color.White)
            "existing", "hold project" -> StatusChipColors(Color(0xFFFF9800), Color.Black)
            "finish" -> StatusChipColors(Color.Gray, Color.White)
            else -> StatusChipColors(Color.Transparent, Color.Black)
        }
    }

    Text(
        text = status,
        color = chipColors.textColor,
        modifier = Modifier
            .background(
                color = chipColors.backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        val colorScheme = MaterialTheme.colorScheme
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Folder else Icons.Outlined.Folder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) colorScheme.primary else colorScheme.outlineVariant
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewProjectCard1() {
    AppTheme {
        ProjectCard(
            project = DataState(
                no = 2,
                project = "INFRASTRUKTUR TRANSPORTASI DARAT DAN LAUT KAWASAN STRATEGIS NASIONAL IKN",
                lastUpdate = "2024-04-24 16:52:36",
                category = "LOW",
                location = "Kawasan Strategis Nasional IKN, Kec. Samboja,. Kertamukti No.4, Kec. Ciputat Timur, Jl – Ciputat ",
                status = "planning",
                statProject = "",
                province = "Jawa Barat",
                idProject = "9531",
                idRecord = "19029-21181/280224/CDI-HRC"
            ),
            onFavoriteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewProjectCard2() {
    AppTheme {
        ProjectCard(
            project = DataState(
                no = 3,
                project = "SISTEM PENGELOLAAN LINGKUNGAN HIDUP BERKELANJUTAN IKN NUSANTARA",
                lastUpdate = "2024-04-24 16:52:36",
                category = "IND",
                location = "Komplek Jakarta Garden City (JGC), Cakung",
                status = "Hold Project",
                statProject = "New",
                province = "D.K.I Jakarta",
                idProject = "9532",
                idRecord = "19029-21181/280224/CDI-HRC"
            ),
            onFavoriteClick = {true},
        )
    }
}


