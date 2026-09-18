package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Premium glassmorphic container with translucent backdrop, gradient borders,
 * dynamic elevation specular reflection, and spring-physics press & hover feedback.
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(16.dp),
    borderWidth: Dp = 1.dp,
    isDark: Boolean = true,
    highlightColor: Color = if (isDark) Color(0xFFDC2626) else Color(0xFFEF4444),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.975f
            isHovered -> 1.012f
            else -> 1.0f
        },
        animationSpec = spring(dampingRatio = 0.72f, stiffness = 420f),
        label = "glass_card_scale"
    )

    // Frosted glass background brush with specular highlight at top-left
    val backgroundBrush = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                Color(0xFF1E293B).copy(alpha = if (isHovered) 0.85f else 0.65f),
                Color(0xFF0F172A).copy(alpha = if (isHovered) 0.90f else 0.75f),
                Color(0xFF090D16).copy(alpha = 0.88f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = if (isHovered) 0.96f else 0.88f),
                Color(0xFFF8FAFC).copy(alpha = if (isHovered) 0.92f else 0.82f),
                Color(0xFFF1F5F9).copy(alpha = 0.85f)
            )
        )
    }

    // Precision perimeter glass sheen
    val borderBrush = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                highlightColor.copy(alpha = if (isHovered) 0.6f else 0.35f),
                Color.White.copy(alpha = if (isHovered) 0.25f else 0.12f),
                Color(0xFF334155).copy(alpha = 0.25f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                highlightColor.copy(alpha = if (isHovered) 0.5f else 0.3f),
                Color.White.copy(alpha = 0.8f),
                Color(0xFFCBD5E1).copy(alpha = 0.5f)
            )
        )
    }

    Box(
        modifier = modifier
            .scale(scale)
            .clip(shape)
            .background(backgroundBrush)
            .border(borderWidth, borderBrush, shape)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = ripple(color = highlightColor.copy(alpha = 0.2f)),
                        onClick = onClick
                    )
                } else Modifier
            ),
        content = content
    )
}

/**
 * Interactive glass button with smooth hover & click micro-interactions.
 */
@Composable
fun GlassButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(12.dp),
    isDark: Boolean = true,
    accentColor: Color = Color(0xFFDC2626),
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isHovered by interactionSource.collectIsHoveredAsState()

    val scale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.94f
            isHovered -> 1.03f
            else -> 1.0f
        },
        animationSpec = spring(dampingRatio = 0.7f, stiffness = 450f),
        label = "glass_button_scale"
    )

    val bgBrush = if (isDark) {
        Brush.linearGradient(
            colors = listOf(
                accentColor.copy(alpha = if (isHovered) 0.28f else 0.16f),
                Color(0xFF1E293B).copy(alpha = if (isHovered) 0.85f else 0.65f)
            )
        )
    } else {
        Brush.linearGradient(
            colors = listOf(
                accentColor.copy(alpha = if (isHovered) 0.22f else 0.12f),
                Color.White.copy(alpha = if (isHovered) 0.95f else 0.85f)
            )
        )
    }

    val borderBrush = Brush.linearGradient(
        colors = listOf(
            accentColor.copy(alpha = if (isHovered) 0.7f else 0.4f),
            Color.White.copy(alpha = if (isDark) 0.15f else 0.6f)
        )
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(shape)
            .background(bgBrush)
            .border(1.dp, borderBrush, shape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(color = accentColor),
                onClick = onClick
            ),
        content = content
    )
}
