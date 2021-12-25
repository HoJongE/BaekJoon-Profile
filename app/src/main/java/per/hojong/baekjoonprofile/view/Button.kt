package per.hojong.baekjoonprofile.view

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundButton(modifier: Modifier?, value: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Black,
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = (modifier ?: Modifier)
            .height(60.dp)
            .aspectRatio(2f)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.body1,
            color = Color.White,
        )
    }
}

@Composable
fun RoundLoadingButton(
    modifier: Modifier = Modifier,
    value: String,
    loading: Boolean,
    backgroundColor: Color = Color.Black,
    disabledBackgroundColor: Color = Color.Black,
    textColor: Color = Color.White,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            disabledBackgroundColor = disabledBackgroundColor
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .height(60.dp)
            .aspectRatio(4f),
        enabled = !loading,
        elevation = null
    ) {
        if (loading) {
            CircularProgressIndicator()
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.body1,
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoundButtonPreview() {
    RoundButton(null, "로그인") {}
}