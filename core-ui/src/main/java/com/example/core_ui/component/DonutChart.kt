package com.example.core_ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.domain.model.DonutData
import kotlinx.collections.immutable.ImmutableList


@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    data: ImmutableList<DonutData>,
    animationDuration: Int = 1500,
    strokeWidth: Float = 40f //thickness donut

) {

    val total = data.sumOf { it.value.toDouble() }.toFloat()
    if (total == 0f) return

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animatedProgress.animateTo(
            1f,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    Canvas(
        modifier = modifier
            .size(110.dp)
            .padding(16.dp)
    ) {
        var startAngle = -90f

        data.forEach { item ->
            val fraction = item.value/total
            val sweep = 360 * fraction * animatedProgress.value

            //Donut arc without fill
            drawArc(
                color = item.color,
                startAngle = startAngle,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )
            startAngle += 360 * fraction
        }
    }
}

/**   //Label Position
 *
 *   //val radius = size.minDimension / 2.1f
 *   //val center = Offset(size.width / 2, size.height / 2)
 *
          val midAngle = startAngle + (sweep / 2)
          val labelRadius = radius - (strokeWidth / 2)
          val labelX =
              center.x + (labelRadius * cos(Math.toRadians(midAngle.toDouble()))).toFloat()
          val labelY =
              center.y + (labelRadius * sin(Math.toRadians(midAngle.toDouble()))).toFloat()

          val percent = (item.value / total * 100).roundToInt()

          drawContext.canvas.nativeCanvas.apply {
              drawText(
                  "$percent%",
                  labelX,
                  labelY + 10f, //down a little so it's level
                  Paint().apply {
                      color = android.graphics.Color.WHITE
                      textAlign = Paint.Align.CENTER
                      textSize = 24f
                      isFakeBoldText = true
                  }
              )
          }*/