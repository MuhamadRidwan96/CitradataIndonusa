package com.example.core_ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.model.DonutData
import kotlinx.collections.immutable.ImmutableList


@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    data: ImmutableList<DonutData>,
    strokeWidth: Float = 40f //thickness donut

) {

    val total = data.sumOf { it.value.toDouble() }.toFloat()
    if (total == 0f) return

    Box(
        modifier = modifier.size(105.dp),
        contentAlignment = Alignment.Center,
    ){
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(8.dp)
        ) {
            var startAngle = -90f

            data.forEach { item ->
                val fraction = item.value/total
                val sweep = 360 * fraction

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = total.toInt().toString(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Total",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
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