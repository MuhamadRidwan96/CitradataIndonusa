package com.example.features.presentation.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FavoriteScreen(name:String, onClick :()->Unit){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

    ) {
        androidx.compose.material.Text(
            modifier = Modifier.clickable { onClick() },
            text = name,
            fontSize = androidx.compose.material.MaterialTheme.typography.h3.fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}