// 파일 경로: app/src/main/java/com.example/groomton_android_a_base/sampledata/SampleDataProvider.kt
package com.example.groomton_android_a_base.sampledata

import com.example.groomton_android_a_base.model.ExploreFeed
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.model.Post
import com.example.groomton_android_a_base.model.Comment
import kotlin.random.Random // ❗ Random을 위해 import 추가 ❗

object SampleDataProvider {
    // 모든 사용자의 마스터 리스트 (유지)
    val sampleUsers = listOf(
        User("hyuk_seong", "내 게시물", "https://picsum.photos/id/101/100/100"),
        User("nigga_nigga", "nigga_nigga", "https://picsum.photos/id/60/100/100"),
        User("chuvelop", "chuvelop", "https://picsum.photos/id/61/100/100"),
        User("jaehoon_king", "jaehoon_king", "https://picsum.photos/id/62/100/100"),
        User("hyeon_0", "hyeun_0", "https://picsum.photos/id/63/100/100"),
        User("kidsung3", "kidsung3", "https://picsum.photos/id/64/100/100"),
        User("jaehun_24", "jaehun_24", "https://picsum.photos/id/65/100/100"),
        User("chu_girl", "chu_girl", "https://picsum.photos/id/66/100/100"),
        User("gang_gang", "gang_gang", "https://picsum.photos/id/67/100/100"),
        User("nigga_nigga", "nigga_nigga", "https://picsum.photos/id/68/100/100"),
        User("goorhm", "goorhm", "https://picsum.photos/id/80/100/100"),
        User("skr_skr", "skr_skr", "https://picsum.photos/id/81/100/100")
    )

    val sampleStoryUsers = sampleUsers.subList(1, sampleUsers.size)
    val sampleHighlightUsers = listOf(
        User("Golf", "Golf", "https://picsum.photos/id/401/100/100"),
        User("Friends", "Friends", "https://picsum.photos/id/402/100/100"),
        User("Daily", "Daily", "https://picsum.photos/id/403/100/100"),
        User("Travel", "Travel", "https://picsum.photos/id/404/100/100"),
        User("Study", "Study", "https://picsum.photos/id/405/100/100"),
        User("Work out", "Work out", "https://picsum.photos/id/406/100/100"),
        User("nigga_nigga", "nigga_nigga", "https://picsum.photos/id/407/100/100") // 7개 요청하셨으니 하나 더 추가
    )
    val sampleExploreFeeds = listOf(
        ExploreFeed(id = "hyuk_seong", imageUrl = "https://picsum.photos/id/200/600/600", user = sampleUsers[1], size = 1),
        ExploreFeed(id = "hyeon_0", imageUrl = "https://picsum.photos/id/201/600/600", user = sampleUsers[2], size = 2),
        ExploreFeed(id = "nigga_nigga", imageUrl = "https://picsum.photos/id/202/600/600", user = sampleUsers[3], size = 1),
        ExploreFeed(id = "chuvelop", imageUrl = "https://picsum.photos/id/203/600/600", user = sampleUsers[4], size = 1),
        ExploreFeed(id = "jaehun_24", imageUrl = "https://picsum.photos/id/204/600/600", user = sampleUsers[5], size = 2),
        ExploreFeed(id = "hyeon_0", imageUrl = "https://picsum.photos/id/205/600/600", user = sampleUsers[6], size = 1),
        ExploreFeed(id = "chuvelop", imageUrl = "https://picsum.photos/id/206/600/600", user = sampleUsers[7], size = 1),
        ExploreFeed(id = "jaehoon_king", imageUrl = "https://picsum.photos/id/207/600/600", user = sampleUsers[8], size = 2),
        ExploreFeed(id = "skr_skr", imageUrl = "https://picsum.photos/id/208/600/600", user = sampleUsers[9], size = 1),
        ExploreFeed(id = "nigga_nigga", imageUrl = "https://picsum.photos/id/209/600/600", user = sampleUsers[10], size = 1),
    )

    // ❗ PostCard에서 사용할 샘플 Post 데이터 생성 함수 ❗
    fun createSamplePost(id: Int): Post {
        val user = sampleUsers[id % sampleUsers.size]
        val postUrl = "https://picsum.photos/id/${id + 100}/600/600"
        val isLiked = id % 2 == 0

        val comments = if (id == 0) listOf(
            Comment("c1", sampleUsers[10], "멋진 사진이네요!", System.currentTimeMillis()),
            Comment("c2", sampleUsers[11], "저도 가보고 싶어요~", System.currentTimeMillis())
        ) else emptyList()

        return Post(
            id = id,
            user = user,
            postImageUrl = postUrl,
            isLiked = isLiked,
            comments = comments,
            // ❗ 좋아요 수를 1부터 99,999까지 랜덤으로 생성 ❗
            likesCount = Random.nextInt(1, 100000)
        )
    }
}