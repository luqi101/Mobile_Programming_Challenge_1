package com.example.healthmonitoringapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale
import kotlin.math.abs

data class ChartPoint(
    val label: String,
    val value: Float
)

@Composable
fun TrendChart(
    title: String,
    points: List<ChartPoint>,
    valueSuffix: String,
    accent: Color,
    modifier: Modifier = Modifier
) {
    val safePoints = points.filter { it.value.isFinite() }
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            if (safePoints.isEmpty()) {
                EmptyState(
                    title = "No chart data",
                    message = "Save logs to build this trend.",
                    modifier = Modifier.height(150.dp)
                )
            } else {
                val min = safePoints.minOf { it.value }
                val max = safePoints.maxOf { it.value }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${min.toNiceLabel()}$valueSuffix",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${max.toNiceLabel()}$valueSuffix",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    val horizontalPadding = 18.dp.toPx()
                    val verticalPadding = 14.dp.toPx()
                    val chartWidth = (size.width - horizontalPadding * 2).coerceAtLeast(1f)
                    val chartHeight = (size.height - verticalPadding * 2).coerceAtLeast(1f)
                    val range = max - min
                    val safeRange = if (abs(range) < 0.001f) 1f else range
                    val gridColor = Color(0xFFE1E8EC)

                    repeat(4) { line ->
                        val y = verticalPadding + chartHeight * (line / 3f)
                        drawLine(
                            color = gridColor,
                            start = Offset(horizontalPadding, y),
                            end = Offset(size.width - horizontalPadding, y),
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    fun pointOffset(index: Int, value: Float): Offset {
                        val x = if (safePoints.size == 1) {
                            size.width / 2f
                        } else {
                            horizontalPadding + chartWidth * (index.toFloat() / (safePoints.lastIndex).toFloat())
                        }
                        val normalized = (value - min) / safeRange
                        val y = verticalPadding + chartHeight - normalized * chartHeight
                        return Offset(x, y)
                    }

                    if (safePoints.size == 1) {
                        val point = pointOffset(0, safePoints.first().value)
                        drawCircle(color = accent.copy(alpha = 0.18f), radius = 12.dp.toPx(), center = point)
                        drawCircle(color = accent, radius = 5.dp.toPx(), center = point)
                    } else {
                        val path = Path()
                        safePoints.forEachIndexed { index, chartPoint ->
                            val offset = pointOffset(index, chartPoint.value)
                            if (index == 0) path.moveTo(offset.x, offset.y) else path.lineTo(offset.x, offset.y)
                        }
                        drawPath(
                            path = path,
                            color = accent,
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                        safePoints.forEachIndexed { index, chartPoint ->
                            val point = pointOffset(index, chartPoint.value)
                            drawCircle(color = Color.White, radius = 6.dp.toPx(), center = point)
                            drawCircle(color = accent, radius = 4.dp.toPx(), center = point)
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = safePoints.first().label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = safePoints.last().label,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun Float.toNiceLabel(): String {
    val asInt = toInt()
    return if (abs(this - asInt.toFloat()) < 0.05f) asInt.toString() else String.format(Locale.US, "%.1f", this)
}
