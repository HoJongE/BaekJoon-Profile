package per.hojong.baekjoonprofile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import per.hojong.baekjoonprofile.model.Background
import per.hojong.baekjoonprofile.model.Badge
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme


@Composable
fun DetailInfoView(profile: Profile) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Profile(profile = profile)
    }
}

@Composable
fun Profile(profile: Profile) {
    Column() {
        Box(modifier = Modifier.padding(0.dp, 24.dp, 0.dp, 12.dp)) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .aspectRatio(1f),
                painter = rememberImagePainter(data = profile.profileImageUrl, builder = {
                    transformations(
                        CircleCropTransformation()
                    )
                }),
                contentDescription = profile.handle,
                contentScale = ContentScale.Crop
            )
        }
        ProfileNameText(value = profile.handle)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DetailInfoPreview() {
    BaekJoonProfileTheme() {
        androidx.compose.material.Surface() {
            Scaffold {
                DetailInfoView(
                    Profile(
                        "as00098", "",
                        Badge("grass_05", "", "", ""),
                        Background("", "", "", ""),
                        "", 5, 512, 11, 11, 11, 11, 35, 3
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfilePreview() {
    Profile(
        Profile(
            "as00098", "",
            Badge("grass_05", "", "", ""),
            Background("", "", "", ""),
            "", 5, 512, 11, 11, 11, 11, 35, 3
        )
    )
}