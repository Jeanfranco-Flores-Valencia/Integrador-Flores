package com.floresvalencia.yourpeak.ui.screens.splash

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.floresvalencia.yourpeak.R
import com.floresvalencia.yourpeak.ui.components.FooterLink
import com.floresvalencia.yourpeak.ui.components.PageIndicator
import com.floresvalencia.yourpeak.ui.components.PeakBackground
import com.floresvalencia.yourpeak.ui.components.PeakButton
import com.floresvalencia.yourpeak.ui.components.PeakLogo
import com.floresvalencia.yourpeak.ui.theme.PeakRed
import com.floresvalencia.yourpeak.ui.theme.PeakRedDark
import com.floresvalencia.yourpeak.ui.theme.YourPeakTheme

@Composable
fun SplashScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    PeakBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PeakLogo(Modifier.align(Alignment.Start))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                RedTriangle(Modifier.fillMaxSize())
                Image(
                    painter = painterResource(R.drawable.splash_athlete),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.95f)
                )
            }

            Spacer(Modifier.height(16.dp))
            PeakButton(
                text = "Iniciar sesion",
                onClick = onLoginClick,
                modifier = Modifier.fillMaxWidth(0.88f)
            )
            Spacer(Modifier.height(10.dp))
            FooterLink(
                prefix = "¿ No tienes una cuenta ?  ",
                action = "REGISTRATE",
                onClick = onRegisterClick
            )
            Spacer(Modifier.height(12.dp))
            PageIndicator(selected = 0)
        }
    }
}

/** Triángulo rojo redondeado detrás del atleta. */
@Composable
private fun RedTriangle(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(w * 0.08f, h * 0.32f)
            lineTo(w * 0.94f, h * 0.24f)
            lineTo(w * 0.56f, h * 0.88f)
            close()
        }
        val brush = Brush.linearGradient(
            colors = listOf(PeakRed, PeakRedDark),
            start = Offset(w * 0.2f, h * 0.2f),
            end = Offset(w * 0.6f, h * 0.9f)
        )
        drawPath(path, brush)
        drawPath(
            path = path,
            color = PeakRed,
            style = Stroke(width = 18.dp.toPx(), join = StrokeJoin.Round)
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 760)
@Composable
private fun SplashPreview() {
    YourPeakTheme { SplashScreen({}, {}) }
}
