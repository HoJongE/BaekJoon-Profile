package per.hojong.baekjoonprofile.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import per.hojong.baekjoonprofile.R

val GMarketSansFamily = FontFamily(
    Font(R.font.gmarket_light, FontWeight.Light),
    Font(R.font.gmarket_medium, FontWeight.Medium),
    Font(R.font.gmarket_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val GMarketTypography = Typography(
    body1 = TextStyle(
        fontFamily = GMarketSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    h1 = TextStyle(
        fontFamily = GMarketSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

