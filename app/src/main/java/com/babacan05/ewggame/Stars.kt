package com.babacan05.ewggame

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size

@Composable
fun AnimatedStarsBackground(
    modifier: Modifier = Modifier,
    starCount: Int = 50,
    starSize: Dp = 2.dp,
    starColor: Color = Color.White,
    animationSpeed: Float = 1.5f,
    boxSize: Dp = 200.dp
) {
    val transition = rememberInfiniteTransition()
    val animatedOffset by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = (starCount * animationSpeed * 1000).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Box(modifier = modifier.fillMaxSize()) {
        repeat(starCount) { index ->
            val x = (animatedOffset * boxSize.value * 2 + index * 1000) % boxSize.value
            val y = (animatedOffset * boxSize.value * 2 + index * 1000) % boxSize.value
            Star(
                modifier = Modifier.offset(x.dp, y.dp),
                size = starSize,
                color = starColor
            )
        }
    }
}

@Composable
fun Star(
    modifier: Modifier = Modifier,
    size: Dp = 2.dp,
    color: Color = Color.White
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
       Icon(imageVector = Icons.Default.Star, contentDescription ="",tint=color )
    }
}