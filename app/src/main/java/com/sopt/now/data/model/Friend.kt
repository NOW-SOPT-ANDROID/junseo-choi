package com.sopt.now.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class Friend(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val profileImageUrl: String,
    val selfDescription: String,
    val profileMusicName: String,
) {
    companion object {
        val defaultFriendInfo =
            Friend(
                name = "",
                profileImageUrl = "",
                selfDescription = "",
                profileMusicName = "",
            )

        val dummyData =
            listOf<Friend>(
                Friend(
                    name = "김아린",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/97820109?v=4",
                    selfDescription = "다들 곽의진 곽의진 하길래 얼마나 잘하는지 봤는데 곽의진 곽의진",
                    profileMusicName = "18번 (Feat. Leellamarz, B.I) - TOIL",
                ),
                Friend(
                    name = "손민재",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/137160756?v=4",
                    selfDescription = "왜 이걸로 웃겨야겠다는 강박을 느끼며 수십분을 고민 중인 것인가.. 왜 영감이 떠오르지 않는다는 사실이 분한 것인가.. 나는 개그맨인가?",
                    profileMusicName = "",
                ),
                Friend(
                    name = "이연진",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/144861180?v=4",
                    selfDescription = "???: 누나 안드 개발자 안할거야? Lee: 하..할ㄱ..ㅓ야..;;;",
                    profileMusicName = "",
                ),
                Friend(
                    name = "곽의진",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/93872496?v=4",
                    selfDescription = "파트장입니다",
                    profileMusicName = "I'm Yours - Jason Mraz",
                ),
                Friend(
                    name = "우상욱",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/113014331?v=4",
                    selfDescription = "간지나게 코딩하기",
                    profileMusicName = "",
                ),
                Friend(
                    name = "이유빈",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/128459613?v=4",
                    selfDescription = "나 이유빈인데 나보다 재밌는 사람 나와봐",
                    profileMusicName = "Siren - RIIZE",
                ),
                Friend(
                    name = "김언지",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/85453429?v=4",
                    selfDescription = "소주잔! 너 내 도도독..",
                    profileMusicName = "",
                ),
                Friend(
                    name = "박동민",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/52882799?v=4",
                    selfDescription = "곽의진...얼굴 재치 실력 모든걸 다 가진 남자... 하지만 밀양박씨 36대손인 나 박동민은 가지지 못했지",
                    profileMusicName = "",
                ),
                Friend(
                    name = "배지현",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/103172971?v=4",
                    selfDescription = "안드로이드 감성 모르면 나가라",
                    profileMusicName = "",
                ),
                Friend(
                    name = "배찬우",
                    profileImageUrl = "https://avatars.githubusercontent.com/u/106955456?v=4",
                    selfDescription = "파워포인트가 목이 없을 때 하는말은? 제..목을 입력해주세요",
                    profileMusicName = "",
                ),
            )
    }
}
