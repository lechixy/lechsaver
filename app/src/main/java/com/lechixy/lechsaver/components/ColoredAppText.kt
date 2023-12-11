package com.lechixy.lechsaver.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.lechixy.lechsaver.R

@Composable
fun ColoredAppText(){
    val currentFontSizePx = with(LocalDensity.current) { 40.sp.toPx() }
    val currentFontSizeDoublePx = currentFontSizePx * 2
    val offset by rememberInfiniteTransition(label = "animate_animation")
        .animateFloat(
            initialValue = 0f,
            targetValue = currentFontSizeDoublePx,
            animationSpec = infiniteRepeatable(
                tween(
                    1500,
                    easing = LinearEasing
                ),
                //RepeatMode.Reverse
            ),
            label = "animate_brush"
        )

    val gradient = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
    )

    Text(
        stringResource(R.string.app_name),
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = gradient,
                start = Offset(offset, offset),
                end = Offset(
                    offset + currentFontSizePx,
                    offset + currentFontSizePx
                ),
                tileMode = TileMode.Mirror
            ),
            shadow = Shadow(
                Color.White,
                Offset(0f, 0f),
                60f
            )
        ),
        fontSize = 22.sp
    )
}