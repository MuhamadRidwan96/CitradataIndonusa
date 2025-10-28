package com.example.core_ui.component


import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun PieChart(
    data: List<PieData>,
    modifier: Modifier = Modifier,
    animatedDuration: Int = 1000
) {

    val total = data.sumOf { it.value.toDouble() }.toFloat()
    val animatedProcess = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        animatedProcess.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = animatedDuration)
        )
    }

    Canvas(
        modifier = modifier
            .size(140.dp)
            .padding(16.dp)
    ) {

        var startAngle = -90f
        var radius = size.minDimension / 2.2f
        var center = Offset(size.width / 2, size.height / 2)

        data.forEach { item ->
            val sweep = 360 * (item.value/ total) * animatedProcess.value

            //Gambar Slice
           drawArc(
               color = item.color,
               startAngle = startAngle,
               sweepAngle =  sweep,
               useCenter = true
           )

            //Hitung posisi label ditengah slice
            val middleAngle = startAngle + (sweep/2)
            val labelRadius = radius * 0.6f
            val labelX = center.x + (labelRadius * cos(Math.toRadians(middleAngle.toDouble()))).toFloat()
            val labelY = center.y + (labelRadius * sin(Math.toRadians(middleAngle.toDouble()))).toFloat()

            //Hitung percentage
            val percent = (item.value/total*100).roundToInt()

            //Tampilkan label percentage
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    "$percent%",
                    labelX,
                    labelY,
                    Paint().apply{
                        color = android.graphics.Color.WHITE
                        textAlign = Paint.Align.CENTER
                        textSize = 32f
                        isFakeBoldText = true
                    }
                )
            }
            startAngle  += 360 *(item.value/total)
        }
    }
}

data class PieData(
    val label: String,
    val value: Float,
    val color: Color
)
