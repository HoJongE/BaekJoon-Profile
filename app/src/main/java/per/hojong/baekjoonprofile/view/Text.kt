package per.hojong.baekjoonprofile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
fun ProfileDetailBody(
    value: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    textAlign: TextAlign = TextAlign.Center
) {
    Text(
        modifier = modifier,
        text = value,
        color = textColor,
        fontSize = 18.sp,
        fontFamily = GMarketSansFamily,
        fontWeight = FontWeight.Medium,
        textAlign = textAlign,
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
        fontSize = 18.sp,
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun ProfileBio(value: String) {
    val bioIsEmpty = value.isEmpty()
    Text(
        text = if (bioIsEmpty) stringResource(id = R.string.self_description_empty) else value,
        Modifier
            .background(Color.White, RoundedCornerShape(8.dp))
            .fillMaxWidth(0.85f)
            .padding(8.dp),
        color = if (bioIsEmpty) Gray else Color.Black,
        textAlign = TextAlign.Start,
        fontFamily = GMarketSansFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    )
}

@Composable
fun ErrorText(error: String) {
    Text(
        modifier = Modifier.padding(8.dp),
        text = error,
        color = Color.Red,
        textAlign = TextAlign.Center,
        fontFamily = GMarketSansFamily,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp
    )
}

