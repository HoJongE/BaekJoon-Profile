package per.hojong.baekjoonprofile.view

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.model.Background
import per.hojong.baekjoonprofile.model.Badge
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme
import per.hojong.baekjoonprofile.ui.theme.GMarketSansFamily
import per.hojong.baekjoonprofile.view.profile.TierBadge
import per.hojong.baekjoonprofile.viewmodel.LoginViewModel


@Composable
fun DetailInfoView(profile: Profile, loginViewModel: LoginViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "프로필", fontWeight = FontWeight.Medium, fontFamily = GMarketSansFamily,
                    color = Color.White
                )
            },
            navigationIcon = {
                Button(
                    onClick = { loginViewModel.loginSuccess = false },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Black
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.all_back),
                        contentDescription = "로그아웃",
                        tint = Color.White
                    )
                }
            },
            elevation = 8.dp, backgroundColor = Color.Black,
        )
        Profile(profile = profile)
    }
}


@Composable
fun Profile(profile: Profile) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier) {
            Image(
                painter = rememberImagePainter(data = profile.background.backgroundImageUrl),
                contentDescription = "백그라운드",
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.25f)
                    .padding(bottom = 8.dp, top = 50.dp)
                    .aspectRatio(1f)
                    .align(Alignment.Center)
                    .shadow(8.dp, CircleShape, clip = true),
                painter = rememberImagePainter(
                    data = profile.profileImageUrl
                        ?: "https://static.solved.ac/misc/360x360/default_profile.png",
                    builder = {
                        transformations(
                            CircleCropTransformation()
                        )
                    }),
                contentDescription = profile.handle,
                contentScale = ContentScale.Crop
            )
            TierBadge(
                tier = profile.tier, modifier =
                Modifier
                    .align(Alignment.BottomCenter)
            )
        }
        ProfileNameText(value = profile.handle)
        ProfileBio(value = profile.bio)
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
                    ), LoginViewModel(LocalContext.current as Application)
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