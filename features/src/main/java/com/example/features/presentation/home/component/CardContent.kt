package com.example.features.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.core_ui.R
import com.example.core_ui.component.StatusChip

@Composable
fun ProjectHeader(
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
fun HeaderText(text: String) {
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
fun ProjectTitle(modifier: Modifier = Modifier, title: String) {
    Column(modifier = modifier.padding(top = 8.dp, bottom = 8.dp, start = 4.dp, end = 0.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ProjectMetadata(
    date: String,
    location: String,
    province: String,
    idProject: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

        TextTittle(
            icon = R.drawable.ic_place_marker,
            text = "Location",
            content = {
                RowLocation(
                    location = location,
                    province = province,
                )
            }
        )

        TextTittle(
            icon = R.drawable.ic_calendar,
            text = "Last Update",
            content = {
                Text(
                    text = date,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        )
        TextComponent(text = stringResource(R.string.id_project)) {
            Text(
                text = idProject,
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun TextComponent(text: String, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(text = text, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            content()
        }
    }
}


@Composable
fun RowLocation(location: String, province: String) {
    Text(
        text = "$location, $province",
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
fun TextTittle(@DrawableRes icon: Int, text: String, content: @Composable () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(14.dp)
                .alignBy(FirstBaseline)
        )
        Column {
            Text(text = text, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            content()
        }
    }
}