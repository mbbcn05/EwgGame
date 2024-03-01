import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.babacan05.ewggame.GameBitmaps
import kotlin.math.roundToInt

@Composable
fun BlinkingRectangleEffect(
    modifier: Modifier = Modifier,
    width: Dp,
    height: Dp,
    offset:Offset,
    strokeWidth: Float,
    borderColor: Color,
    radius: Float
) {
    var isBlinking by remember { mutableStateOf(true) }

    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier//.background(Color.Blue)
                .offset((offset.x/GameBitmaps.density).dp,(offset.y/GameBitmaps.density).dp)
                .drawBehind {
                    val brush = Brush.radialGradient(
                        colors = listOf(Color.Blue.copy(alpha=0.7f), Color.White,Color.Green),
                        center = Offset.Zero,
                        radius = radius * 2
                    )
                    val alphaValue = if (isBlinking) alpha else 0.2f
                    drawRoundRect(
                        color = borderColor,
                        size = Size(width.toPx(), height.toPx()),
                        cornerRadius = CornerRadius(38f,8f),
                        style = Stroke(width = strokeWidth)
                    )
                    drawRoundRect(

                        brush = brush,
                        alpha = alphaValue,
                        size = Size(width.toPx(), height.toPx()),
                        cornerRadius = CornerRadius(38f,38f),
                       // style = androidx.compose.ui.graphics.drawscope.DrawScope.EmptyBucket
                    )
                }
        )
    }
}