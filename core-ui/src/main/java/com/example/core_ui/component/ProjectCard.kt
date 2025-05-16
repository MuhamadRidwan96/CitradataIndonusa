package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.AppTheme
import com.example.core_ui.R


@Composable
fun ProjectCard(
    items: CardItem,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.13f))
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            // Title and Status
            HeaderProject(
                statusProject = items.statusProject,
                isFavorite = isFavorite,
                onFavoriteClick = onFavoriteClick
            )

            Text(
                text = items.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            ProjectDateAndCategory(
                date =items.date,
                category = items.category
            )

            LocationProject(
                location = items.location,
                status = items.status
            )
        }
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Bookmarks else Icons.Outlined.Bookmarks,
            contentDescription = if (isFavorite) "Removes from favorite" else "Add to Favorite",
            tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Composable
fun HeaderProject(statusProject: String, isFavorite: Boolean, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        StatusIndicator(statusProject)

        FavoriteButton(
            isFavorite = isFavorite,
            onClick = onFavoriteClick
        )
    }
}

@Composable
fun LocationProject(location: String,status: String) {
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        IconTextRow(iconResId = R.drawable.ic_location, text = location, modifier = Modifier.weight(1f))
        StatusProject(status = status)
    }

}

@Composable
fun ProjectDateAndCategory(date: String, category: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconTextRow(iconResId = R.drawable.ic_calendar, text = date)
        IconTextRow(iconResId = R.drawable.ic_category, text = category)
    }
}


@Composable
fun IconTextRow(
    modifier: Modifier = Modifier,
    iconResId: Int,
    text: String,
    iconTint: Color = MaterialTheme.colorScheme.onBackground,
    textColor: Color = MaterialTheme.colorScheme.onBackground,

) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(2.dp)) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun StatusIndicator(status: String) {
    val statusColor = when (status) {
        "New" -> Color.Green
        "Update" -> MaterialTheme.colorScheme.scrim
        else -> Color.Transparent // Default color for unknown status
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(statusColor)
        )
        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = status,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

data class CardItem(
    val title: String,
    val date: String,
    val category: String,
    val location: String,
    val statusProject: String,
    val status: String
)


@Composable
fun StatusProject(status:String){
    val backgroundColor = when (status.lowercase()) {
        "planning" -> Color(0xFF4CAF50) // Hijau
        "post tender" -> Color(0xFFFFEB3B) // Kuning
        "pilling work" -> Color(0xFFFFEB3B) // Kuning
        "construction start" -> Color(0xFFF44336) // Merah
        "under construction" -> Color(0xFF2196F3) // Biru
        "existing" -> Color(0xFFFF9800) // Orange
        "hold project" -> Color(0xFFFF9800) // Orange
        "project canceled" -> Color(0xFFF44336) // Merah
        "final project" -> Color(0xFF2196F3) // Biru
        "finish" -> Color.Gray
        else -> Color.Transparent
    }

    val textColor = if (backgroundColor == Color(0xFFFFEB3B) || backgroundColor == Color(0xFFFF9800)) {
        Color.Black // Untuk warna kuning/orange agar teks tetap terbaca
    } else {
        Color.White
    }

    Text(
        text = status,
        color = textColor,
        modifier = Modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCard(){
    AppTheme {
        ProjectCard(
            items =  CardItem(
                title = "HOTEL - HOTEL MAWAR MELATI",
                date = "Jun 15, 2023",
                category = "Middle Project",
                location = "Downtown, Tokyo Downtown, Tokyo",
                status = "Under Construction",
                statusProject = "Update"
            ),
            isFavorite = true
        ) { }
    }
}
