package com.example.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core_ui.R

@Composable
fun ProjectCard(
    title: String,
    date: String,
    category: String,
    location: String,
    status: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title and Status
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, // Pastikan warna teks terlihat
                    modifier = Modifier.weight(1f)
                )
                StatusIndicator(status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Date and Category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconTextRow(iconResId = R.drawable.ic_calendar, text = date)
                IconTextRow(iconResId = R.drawable.ic_category, text = category)
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Location
            IconTextRow(iconResId = R.drawable.ic_location, text = location)
        }
    }
}

@Composable
fun IconTextRow(
    iconResId: Int,
    text: String,
    iconTint: Color = Color.Black,
    textColor: Color = Color.Black
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
            color = textColor
        )
    }
}

@Composable
fun StatusIndicator(status: String) {
    val statusColor = when (status) {
        "Active" -> Color.Green
        "Inactive" -> Color.Gray
        else -> Color.Gray // Default color for unknown status
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
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

@Preview(showBackground = true, backgroundColor = 0xFFECECEC)
@Composable
fun PreviewProjectCard() {
    ProjectCard(
        title = "Harbor Tower Development",
        date = "Jun 15, 2023",
        category = "Construction",
        location = "Downtown, Tokyo",
        status = "Active"
    )
}