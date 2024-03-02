package com.babacan05.ewggame.gamefiles

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StarBurst(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 1000
                0.1f at 0 with LinearOutSlowInEasing
                1.0f at 1000 with LinearOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(modifier = modifier.size(starBurstSize)) {
        val colors = listOf(Color.Yellow, Color.Red, Color.Blue)
        val strokeWidth = 3f

        val radius = size.minDimension / 2
        val numLines = 12
        val angleStep = 360f / numLines

        for (i in 0 until numLines) {
            val angle = i * angleStep
            val startX = cos(Math.toRadians(angle.toDouble())).toFloat() * radius + center.x
            val startY = sin(Math.toRadians(angle.toDouble())).toFloat() * radius + center.y

            val endX = cos(Math.toRadians(angle.toDouble())).toFloat() * (radius + 30 * scale) + center.x
            val endY = sin(Math.toRadians(angle.toDouble())).toFloat() * (radius + 30 * scale) + center.y

            drawLine(color = colors[i % colors.size], start = Offset(startX, startY), end = Offset(endX, endY), strokeWidth = strokeWidth)
        }
    }
}

val starBurstSize = 4.dp