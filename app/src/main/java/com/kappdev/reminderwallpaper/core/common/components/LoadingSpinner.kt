package com.kappdev.reminderwallpaper.core.common.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingSpinner(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    strokeWidth: Dp = 2.dp,
    cycleDuration: Int = 1_500,
    strokeColor: Color = MaterialTheme.colorScheme.primary
) {
    val transition = rememberInfiniteTransition()
    val spinAngel = transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = cycleDuration,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier = modifier
            .size(size)
            .rotate(spinAngel.value)
    ) {
        drawArc(
            color = strokeColor,
            startAngle = -70f,
            sweepAngle = 140f,
            useCenter = false,
            size = this.size,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )

        drawArc(
            color = strokeColor,
            startAngle = -110f,
            sweepAngle = -140f,
            useCenter = false,
            size = this.size,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}