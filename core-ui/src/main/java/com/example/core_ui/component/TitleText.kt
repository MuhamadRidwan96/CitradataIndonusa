package com.example.core_ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TextTitle(@DrawableRes icon: Int, title: String, desc: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Image(
            painter = painterResource(icon),
            modifier = Modifier.size(22.dp)
                .alignBy(FirstBaseline)
                ,
            contentDescription = null
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = desc,
                color = Color.Gray,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}
