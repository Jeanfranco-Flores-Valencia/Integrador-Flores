package com.floresvalencia.yourpeak.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.floresvalencia.yourpeak.ui.theme.PeakBackground

/** Fondo oscuro con el patrón tenue de mancuernas del diseño. */
@Composable
fun PeakBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PeakBackground)
            .drawBehind { drawDumbbellPattern() },
        content = content
    )
}

private fun DrawScope.drawDumbbellPattern() {
    val color = Color.White.copy(alpha = 0.04f)
    val step = 72.dp.toPx()
    val unit = 3.5.dp.toPx()
    var row = 0
    var y = step / 2
    while (y < size.height + step) {
        var x = if (row % 2 == 0) step / 2 else step
        var col = 0
        while (x < size.width + step) {
            val center = Offset(x, y)
            val angle = if ((row + col) % 2 == 0) -35f else 40f
            rotate(angle, pivot = center) { drawDumbbell(center, unit, color) }
            x += step
            col++
        }
        y += step * 0.9f
        row++
    }
}

private fun DrawScope.drawDumbbell(c: Offset, u: Float, color: Color) {
    val radius = CornerRadius(u * 0.3f)
    // Barra
    drawRect(color, Offset(c.x - 3 * u, c.y - u * 0.35f), Size(6 * u, u * 0.7f))
    // Discos
    drawRoundRect(color, Offset(c.x + 3 * u, c.y - 1.6f * u), Size(u, 3.2f * u), radius)
    drawRoundRect(color, Offset(c.x - 4 * u, c.y - 1.6f * u), Size(u, 3.2f * u), radius)
    drawRoundRect(color, Offset(c.x + 4 * u, c.y - u), Size(u * 0.7f, 2 * u), radius)
    drawRoundRect(color, Offset(c.x - 4.7f * u, c.y - u), Size(u * 0.7f, 2 * u), radius)
}
