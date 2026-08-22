package com.example.core_ui.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer


fun Modifier.toWhiteIf(condition : Boolean) : Modifier {
    return if (condition) {
        this.then(
            Modifier
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()
                    drawRect(Color.White, blendMode = BlendMode.SrcIn)
                }
        )
    } else {
        this
    }
}
