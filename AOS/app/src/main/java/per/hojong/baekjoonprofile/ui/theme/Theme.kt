package per.hojong.baekjoonprofile.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.Black,
    primaryVariant = Color.Black,
    onPrimary = Color.White,
    secondary = Color.Black,
    background = BackgroundColor,
    onBackground = Color.White,
    error = Ruby,
    onError = Color.White
)

private val LightColorPalette = lightColors(
    primary = Color.Black,
    onPrimary = Color.White,
    primaryVariant = Color.Black,
    secondary = Color.Black,
    background = BackgroundColor,
    onBackground = Color.White,
    error = Ruby,
    onError = Color.White
)

@Composable
fun BaekJoonProfileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = GMarketTypography,
        shapes = Shapes,
        content = content
    )
}