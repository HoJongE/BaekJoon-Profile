package per.hojong.baekjoonprofile.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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