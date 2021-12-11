package per.hojong.baekjoonprofile.view

import android.text.TextPaint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.ui.theme.GMarketSansFamily
import per.hojong.baekjoonprofile.ui.theme.Gray

@Composable
fun TitleText(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center,
        color = Gray
    )
}

@Composable
fun BodyText(value: String) {
    Text(
        text = value,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        color = Gray
    )
}

@Composable
fun ProfileNameText(value: String) {
    Text(
        text = value,
        textAlign = TextAlign.Center,
        color = Color.White,
        fontFamily = GMarketSansFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
}