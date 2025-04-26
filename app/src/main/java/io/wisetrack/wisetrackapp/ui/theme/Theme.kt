package io.wisetrack.wisetrackapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF3D5AFE), // IndigoAccent.shade400
    background = Color(0xFFF0EFF8),
    surface = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black,
    outline = Color(0xFFD0D0D0), // Grey.shade400 for divider
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF3D5AFE),
    background = Color(0xFF121115),
    surface = Color(0xFF24202B),
    onSurface = Color.White,
    onBackground = Color.White,
    outline = Color(0xFF4C4954),
)

@Composable
fun WiseTrackTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}