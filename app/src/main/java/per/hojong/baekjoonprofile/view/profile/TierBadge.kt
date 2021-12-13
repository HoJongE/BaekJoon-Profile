package per.hojong.baekjoonprofile.view.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.model.Profile
import per.hojong.baekjoonprofile.ui.theme.*


@Composable
fun TierBadge(tier: Int, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            modifier = Modifier
                .align(Alignment.Center),
            painter = painterResource(id = getTierImage(tier)),
            contentDescription = "tier"
        )
        if (tier != 31) {
            Text(
                text = Profile.getTierNumber(tier).toString(),
                fontFamily = GMarketSansFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 6.dp)
            )
        }
    }
}

fun getTierImage(tier: Int) =
    when (tier) {
        in 1..5 -> R.drawable.profile_tier_bronze
        in 6..10 -> R.drawable.profile_tier_silver
        in 11..15 -> R.drawable.profile_tier_gold
        in 16..20 -> R.drawable.profile_tier_platinum
        in 21..25 -> R.drawable.profile_tier_diamond
        in 26..30 -> R.drawable.profile_tier_ruby
        else -> R.drawable.app_logo
    }


@Composable
@Preview
fun TierBadgePreview() {
    androidx.compose.material.Surface(modifier = Modifier.fillMaxSize()) {
        TierBadge(28)

    }
}