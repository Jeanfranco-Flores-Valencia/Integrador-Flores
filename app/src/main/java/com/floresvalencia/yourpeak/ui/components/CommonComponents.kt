package com.floresvalencia.yourpeak.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.floresvalencia.yourpeak.R
import com.floresvalencia.yourpeak.ui.theme.DotInactive
import com.floresvalencia.yourpeak.ui.theme.PeakBackground
import com.floresvalencia.yourpeak.ui.theme.PeakCard
import com.floresvalencia.yourpeak.ui.theme.PeakLogo
import com.floresvalencia.yourpeak.ui.theme.PeakRed
import com.floresvalencia.yourpeak.ui.theme.TextPrimary
import com.floresvalencia.yourpeak.ui.theme.TextSecondary

/**
 * Estructura base de las pantallas de autenticación:
 * contenido superior + tarjeta inferior, con scroll cuando aparece el teclado.
 */
@Composable
fun AuthScaffold(
    top: @Composable ColumnScope.() -> Unit,
    bottom: @Composable ColumnScope.() -> Unit
) {
    PeakBackground {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .heightIn(min = maxHeight)
                    .padding(horizontal = 18.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.fillMaxWidth(), content = top)
                Column(Modifier.fillMaxWidth().padding(top = 24.dp), content = bottom)
            }
        }
    }
}

@Composable
fun PeakLogo(modifier: Modifier = Modifier, size: Dp = 40.dp) {
    Icon(
        painter = painterResource(R.drawable.ic_logo),
        contentDescription = "Logo Your Peak",
        tint = PeakLogo,
        modifier = modifier.size(size)
    )
}

@Composable
fun HeaderTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = PeakRed,
        lineHeight = 36.sp
    )
}

@Composable
fun AuthCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(PeakCard)
            .padding(horizontal = 18.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun CardMessage(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = PeakRed,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 12.dp)
    )
}

@Composable
fun PageIndicator(selected: Int, modifier: Modifier = Modifier, count: Int = 3) {
    Row(modifier, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(count) { index ->
            Box(
                Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(if (index == selected) PeakRed else DotInactive)
            )
        }
    }
}

/** "¿ No tienes una cuenta ? REGISTRATE" */
@Composable
fun FooterLink(prefix: String, action: String, onClick: () -> Unit) {
    Text(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = TextSecondary)) { append(prefix) }
            withStyle(SpanStyle(color = PeakRed, fontWeight = FontWeight.Bold)) { append(action) }
        },
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
            .padding(4.dp)
    )
}

/** Separador con el botón circular de Google en el centro. */
@Composable
fun SocialDivider(onGoogleClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(0.85f),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(Modifier.weight(1f), thickness = 2.dp, color = TextSecondary.copy(alpha = 0.5f))
        Box(
            modifier = Modifier
                .padding(horizontal = 14.dp)
                .size(26.dp)
                .clip(CircleShape)
                .background(PeakRed.copy(alpha = 0.15f))
                .clickable(onClick = onGoogleClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "G",
                color = PeakRed,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall
            )
        }
        HorizontalDivider(Modifier.weight(1f), thickness = 2.dp, color = TextSecondary.copy(alpha = 0.5f))
    }
}

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Filled.ArrowBackIosNew,
            contentDescription = "Atrás",
            tint = TextPrimary,
            modifier = Modifier.size(20.dp)
        )
    }
}

/** Ícono grande rojo con el halo luminoso de las pantallas de recuperación. */
@Composable
fun GlowIcon(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(modifier.size(220.dp), contentAlignment = Alignment.Center) {
        Canvas(Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PeakRed.copy(alpha = 0.35f), Color.Transparent),
                    radius = size.minDimension / 2
                )
            )
            drawCircle(color = PeakBackground, radius = size.minDimension * 0.33f)
            drawCircle(color = Color.White.copy(alpha = 0.03f), radius = size.minDimension * 0.33f)
        }
        content()
    }
}
