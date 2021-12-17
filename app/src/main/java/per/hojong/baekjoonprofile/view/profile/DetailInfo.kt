package per.hojong.baekjoonprofile.view

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.transform.CircleCropTransformation
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.constant.CLASS_IMAGE_POSTFIX
import per.hojong.baekjoonprofile.constant.CLASS_IMAGE_PREFIX
import per.hojong.baekjoonprofile.model.Background
import per.hojong.baekjoonprofile.model.Badge
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.ui.theme.BaekJoonProfileTheme
import per.hojong.baekjoonprofile.ui.theme.GMarketSansFamily
import per.hojong.baekjoonprofile.ui.theme.Gray
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
        ProfileDetailView(profile = profile)

        ProfileDetailInfo(
            title = "Solved",
            content = profile.solvedCount.toString(),
            profile = profile
        )
        ProfileDetailInfo(
            title = "AC Rating",
            content = profile.rating.toString(),
            profile = profile
        )
        ProfileDetailInfo(
            title = "Rank",
            content = Profile.getTierName(profile.tier) + " " + Profile.getTierNumber(profile.tier) + " " + profile.rank.toString(),
            profile = profile
        )
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
                painter = rememberImagePainter(data = profile.background?.backgroundImageUrl),
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


@Composable
fun ProfileDetailView(profile: Profile) {
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .componentRegistry {
            add(SvgDecoder(LocalContext.current))
        }
        .build()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileDetailBody(value = "CLASS")
            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                val painter =
                    rememberImagePainter(
                        if (profile.classDecoration == "none")
                            CLASS_IMAGE_PREFIX + profile.Class
                                    + CLASS_IMAGE_POSTFIX
                        else CLASS_IMAGE_PREFIX + profile.Class + profile.classDecoration[0] + CLASS_IMAGE_POSTFIX
                    )
                Image(
                    painter = painter,
                    contentDescription = "class",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(2.dp)
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileDetailBody(value = "BADGE")
            if (profile.badge?.badgeImageUrl != null) {
                Image(
                    painter = rememberImagePainter(
                        data = profile.badge.badgeImageUrl,
                    ),
                    contentDescription = "badge",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(2.dp)
                )
            } else {
                Box(
                    modifier = Modifier.height(50.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ProfileDetailBody(
                        value = "배지가\n없습니다.",
                        textColor = Gray
                    )
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ProfileDetailBody(value = "STREAK")
            Box(modifier = Modifier.height(50.dp), contentAlignment = Alignment.Center) {
                ProfileDetailBody(value = profile.maxStreak.toString())
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfilePreview() {
    ProfileDetailView(profile = Profile.dummyProfile())
}

@Composable
fun ProfileDetailInfo(title: String, content: String, profile: Profile) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(top = 16.dp, bottom = 16.dp),
    ) {
        ProfileDetailBody(
            value = title,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        ProfileDetailBody(
            value = content,
            textAlign = TextAlign.Start,
            textColor = Profile.getTierColor(profile.tier),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}