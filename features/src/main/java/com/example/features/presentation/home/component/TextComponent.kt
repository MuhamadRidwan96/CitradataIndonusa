package com.example.features.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core_ui.component.IconBackground
import com.example.features.presentation.home.utils.formatToRupiah

@Composable
fun TitleSection(
    @DrawableRes icon: Int,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, top = 12.dp)
    ) {
        IconBackground(icon)
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun InfoItem(title: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            color = Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )
        content()
    }
}

@Composable
fun CurrencyText(price: String?) {
    Text(
        text = formatToRupiah(price),
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextMain(text: String) {

    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
    )
}





