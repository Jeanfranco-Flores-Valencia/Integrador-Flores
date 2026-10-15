package com.floresvalencia.yourpeak.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.floresvalencia.yourpeak.ui.theme.PeakRed
import com.floresvalencia.yourpeak.ui.theme.PeakRedDark

/** Botón rojo con borde oscuro y texto en mayúsculas. */
@Composable
fun PeakButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    val shape = RoundedCornerShape(10.dp)
    Button(
        onClick = onClick,
        enabled = !isLoading,
        shape = shape,
        border = BorderStroke(2.dp, PeakRedDark),
        colors = ButtonDefaults.buttonColors(
            containerColor = PeakRed,
            contentColor = Color.White,
            disabledContainerColor = PeakRed.copy(alpha = 0.7f),
            disabledContentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 0.dp),
        modifier = modifier
            .height(44.dp)
            .shadow(8.dp, shape, ambientColor = PeakRed, spotColor = PeakRed)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = text.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }
    }
}
