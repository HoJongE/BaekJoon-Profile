package per.hojong.baekjoonprofile.model

import com.google.gson.annotations.SerializedName
import per.hojong.baekjoonprofile.R
import per.hojong.baekjoonprofile.ui.theme.*

data class Profile(
    val handle: String, //사용자 명
    val bio: String, //사용자 자기소개
    val badge: Badge?, //사용자 배지
    val background: Background?, //사용자 배경화면
    val profileImageUrl: String?, //사용자 프로필 하이퍼링크
    @SerializedName("class")
    val Class: Int,
    val classDecoration: String,
    val solvedCount: Int, //사용자가 푼 문제 카운트
    val voteCount: Int, //기여 횟수
    val exp: Long, //경험치 획득량
    val tier: Int, //사용자 티어
    val rating: Int, //사용자의 레이팅
    val maxStreak: Int, //최대 연속 문제 풀이일 수
    val rank: Int, //사용자의 순위
) {
    companion object Tier {
        fun dummyProfile() = Profile(
            "as00098", "",
            Badge("grass_05", "", "", ""),
            Background("", "", "", ""),
            "", 5, "gold", 512, 11, 11, 11, 11, 35, 3
        )

        fun getTierName(tier: Int) = when (tier) {
            in 1..5 -> "Bronze"
            in 6..10 -> "Silver"
            in 11..15 -> "Gold"
            in 16..20 -> "Platinum"
            in 21..25 -> "Diamond"
            in 26..30 -> "Ruby"
            else -> "Master"
        }

        fun getTierColor(tier: Int) = when (tier) {
            in 1..5 -> Bronze
            in 6..10 -> Silver
            in 11..15 -> Gold
            in 16..20 -> Platinum
            in 21..25 -> Diamond
            else -> Ruby
        }

        fun getTierBackgroundId(tier: Int): Int = when (tier) {
            in 1..5 -> R.drawable.round_corner_shape_bronze
            in 6..10 -> R.drawable.round_corner_shape_silver
            in 11..15 -> R.drawable.round_corner_shape_gold
            in 16..20 -> R.drawable.round_corner_shape_platinum
            in 21..25 -> R.drawable.round_corner_shape_diamond
            in 26..30 -> R.drawable.round_corner_shape_ruby
            else -> R.drawable.round_corner_shape_master
        }

        fun getTierNumber(tier: Int) = if (tier % 5 == 0) 1 else 6 - (tier % 5)
    }
}


data class Badge(
    val badgeId: String?,
    val badgeImageUrl: String?,
    val displayName: String?,
    val displayDescription: String?
)

data class Background(
    val backgroundImageUrl: String?,
    val author: String?,
    val displayName: String?,
    val displayDescription: String?
)

