package per.hojong.baekjoonprofile.model

data class Profile(
    val handle: String, //사용자 명
    val bio: String, //사용자 자기소개
    val badge: Badge, //사용자 배지
    val profileImageUrl: String, //사용자 프로필 하이퍼링크
    val solvedCount: Int, //사용자가 푼 문제 카운트
    val voteCount: Int, //기여 횟수
    val exp: Int, //경험치 획득량
    val tier: Int, //사용자 티어 TODO: 티어 분류 필요!
    val rating: Int, //사용자의 레이팅
    val maxStreak: Int, //최대 연속 문제 풀이일 수
    val rank: Int //사용자의 순위
)

data class Badge(
    val badgeId: String?,
    val badgeImageUrl: String?,
    val displayName: String?,
    val displayDescription: String?
)


