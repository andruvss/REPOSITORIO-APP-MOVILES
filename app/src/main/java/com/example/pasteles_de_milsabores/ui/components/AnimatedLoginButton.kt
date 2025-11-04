package com.example.pasteles_de_milsabores.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.pasteles_de_milsabores.ui.theme.LilaPastel
import com.example.pasteles_de_milsabores.ui.theme.TextoGris

@Composable
fun AnimatedLoginButton(
    text: String,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }

    // Animación de escala (rebote)
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.9f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = ""
    )

    Button(
        onClick = {
            pressed = true
            onClick()
        },
        colors = ButtonDefaults.buttonColors(containerColor = LilaPastel),
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .padding(top = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            color = TextoGris,
            fontWeight = FontWeight.Bold
        )
    }

    // Restaurar el estado tras la animación
    LaunchedEffect(pressed) {
        if (pressed) {
            delay(150)
            pressed = false
        }
    }
}
