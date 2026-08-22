package com.example.features.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.domain.model.TrendProject
import com.example.features.presentation.home.utils.formatMonthLabel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.continuous
import com.patrykandpatrick.vico.compose.cartesian.layer.point
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.shapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shader.verticalGradient
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlinx.collections.immutable.ImmutableList


@Composable
fun LineChart(
    trendIProject: ImmutableList<TrendProject>,
    modifier: Modifier = Modifier
) {
    if (trendIProject.isEmpty()) return

    val modelProducer = remember { CartesianChartModelProducer() }

    val labels = remember(trendIProject) {
        trendIProject.map { formatMonthLabel(it.month) }
    }

    val chartValues = remember(trendIProject) {
        trendIProject.map { it.total.toFloat() }
    }

    LaunchedEffect(chartValues) {
        modelProducer.runTransaction {
            lineSeries {
                series(chartValues)
            }
        }
    }

    val lineColor = MaterialTheme.colorScheme.primary


    val pointProvider = remember {
        LineCartesianLayer.PointProvider.single(
            LineCartesianLayer.point(
                component = shapeComponent(
                    fill = fill(lineColor),
                    strokeFill = fill(lineColor),
                    strokeThickness = 2.dp,
                    shape = CorneredShape.Pill,
                ),
                size = 6.dp
            )
        )
    }

    val line = LineCartesianLayer.rememberLine(
        fill = LineCartesianLayer.LineFill.single(
            fill(lineColor)
        ),
        stroke = LineCartesianLayer.LineStroke.continuous(
            thickness = 2.dp
        ),
        pointProvider = pointProvider,
        pointConnector = LineCartesianLayer.PointConnector.cubic(),
        //Gradient warna di bawah kurva
        areaFill = LineCartesianLayer.AreaFill.single(
            fill(
                ShaderProvider.verticalGradient(
                    arrayOf(
                        lineColor.copy(alpha = 0.18f),
                        lineColor.copy(alpha = 0.08f),
                        Color.Transparent
                    )
                )
            )
        )
    )

    val chart = rememberCartesianChart(
        rememberLineCartesianLayer(
            LineCartesianLayer.LineProvider.series(
                line
            )
        ),
        startAxis = VerticalAxis.rememberStart(),

        bottomAxis = HorizontalAxis.rememberBottom(
            guideline = null,
            tick = null,
            valueFormatter = { _, value, _ ->
                labels.getOrNull(value.toInt()).orEmpty()
            }
        )
        )

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(20.dp)

    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Trend Project Overview",
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleSmall

            )
            Spacer(modifier = Modifier.height(16.dp))

            CartesianChartHost(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(180.dp),
                chart = chart,
                modelProducer = modelProducer,
                animationSpec = null,
                zoomState = rememberVicoZoomState(
                    zoomEnabled = false
                )
            )
        }
    }
}


