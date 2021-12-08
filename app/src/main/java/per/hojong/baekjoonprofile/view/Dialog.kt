package per.hojong.baekjoonprofile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import per.hojong.baekjoonprofile.ui.theme.DialogBackground
import per.hojong.baekjoonprofile.ui.theme.Gray


@Composable
fun ProgressDialog() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DialogBackground),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.3f),
            backgroundColor = Gray
        ) {

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun progressDialogPreview() {
    ProgressDialog()
}