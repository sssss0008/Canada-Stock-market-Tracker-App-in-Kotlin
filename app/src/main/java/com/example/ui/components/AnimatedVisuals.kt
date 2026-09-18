package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Animated Maple Leaf brand emblem with pulsating institutional glow.
 */
@Composable
fun AnimatedMapleEmblem(
    modifier: Modifier = Modifier,
    size: Dp = 34.dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "maple_glow")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400),
            repeatMode = RepeatMode.Reverse
        ),
        label = "maple_glow_alpha"
    )

    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Outer glow ambient circle
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFDC2626).copy(alpha = glowAlpha * 0.45f),
                    Color(0xFFEF4444).copy(alpha = glowAlpha * 0.15f),
                    Color.Transparent
                ),
                center = Offset(w / 2f, h / 2f),
                radius = w * 0.72f
            )
        )

        // Rounded badge container
        drawRoundRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFDC2626),
                    Color(0xFF991B1B),
                    Color(0xFF1E293B)
                ),
                start = Offset(0f, 0f),
                end = Offset(w, h)
            ),
            cornerRadius = CornerRadius(w * 0.28f, w * 0.28f)
        )

        // Glassmorphic border outline with highlight
        drawRoundRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.White.copy(alpha = 0.45f),
                    Color(0xFFFCA5A5).copy(alpha = 0.3f),
                    Color.Transparent
                )
            ),
            cornerRadius = CornerRadius(w * 0.28f, w * 0.28f),
            style = Stroke(width = 1.2.dp.toPx())
        )

        // Canadian Maple Leaf stylized geometric silhouette
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.22f)
            lineTo(w * 0.54f, h * 0.35f)
            lineTo(w * 0.62f, h * 0.32f)
            lineTo(w * 0.59f, h * 0.42f)
            lineTo(w * 0.72f, h * 0.42f)
            lineTo(w * 0.65f, h * 0.50f)
            lineTo(w * 0.76f, h * 0.55f)
            lineTo(w * 0.65f, h * 0.62f)
            lineTo(w * 0.68f, h * 0.67f)
            lineTo(w * 0.58f, h * 0.65f)
            lineTo(w * 0.55f, h * 0.73f)
            lineTo(w * 0.53f, h * 0.82f) // stem
            lineTo(w * 0.47f, h * 0.82f)
            lineTo(w * 0.45f, h * 0.73f)
            lineTo(w * 0.42f, h * 0.65f)
            lineTo(w * 0.32f, h * 0.67f)
            lineTo(w * 0.35f, h * 0.62f)
            lineTo(w * 0.24f, h * 0.55f)
            lineTo(w * 0.35f, h * 0.50f)
            lineTo(w * 0.28f, h * 0.42f)
            lineTo(w * 0.41f, h * 0.42f)
            lineTo(w * 0.38f, h * 0.32f)
            lineTo(w * 0.46f, h * 0.35f)
            close()
        }

        drawPath(
            path = path,
            color = Color.White
        )
    }
}

/**
 * Premium custom vector Icon composables for Financial charts, Heatmaps, Screeners, and Hubs.
 */
@Composable
fun CandlestickChartVector(
    modifier: Modifier = Modifier,
    tint: Color = Color(0xFF10B981)
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Candlestick 1 (Green Bullish)
        drawLine(
            color = tint,
            start = Offset(w * 0.25f, h * 0.15f),
            end = Offset(w * 0.25f, h * 0.85f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.16f, h * 0.32f),
            size = Size(w * 0.18f, h * 0.38f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )

        // Candlestick 2 (Red Bearish)
        val redTint = Color(0xFFEF4444)
        drawLine(
            color = redTint,
            start = Offset(w * 0.55f, h * 0.25f),
            end = Offset(w * 0.55f, h * 0.90f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawRoundRect(
            color = redTint,
            topLeft = Offset(w * 0.46f, h * 0.38f),
            size = Size(w * 0.18f, h * 0.32f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )

        // Candlestick 3 (Green Tall Bullish)
        drawLine(
            color = tint,
            start = Offset(w * 0.82f, h * 0.10f),
            end = Offset(w * 0.82f, h * 0.78f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
        drawRoundRect(
            color = tint,
            topLeft = Offset(w * 0.73f, h * 0.20f),
            size = Size(w * 0.18f, h * 0.42f),
            cornerRadius = CornerRadius(2.dp.toPx(), 2.dp.toPx())
        )
    }
}
