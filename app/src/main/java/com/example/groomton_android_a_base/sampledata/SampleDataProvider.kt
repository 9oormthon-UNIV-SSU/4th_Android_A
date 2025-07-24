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
        User("my_user", "MyStory", "https://picsum.photos/id/101/100/100"),
        User("user1", "user1", "https://picsum.photos/id/60/100/100"),
        User("user2", "user2", "https://picsum.photos/id/61/100/100"),
        User("user3", "user3", "https://picsum.photos/id/62/100/100"),
        User("user4", "user4", "https://picsum.photos/id/63/100/100"),
        User("user5", "user5", "https://picsum.photos/id/64/100/100"),
        User("user6", "user6", "https://picsum.photos/id/65/100/100"),
        User("user7", "user7", "https://picsum.photos/id/66/100/100"),
        User("user8", "user8", "https://picsum.photos/id/67/100/100"),
        User("user9", "user9", "https://picsum.photos/id/68/100/100"),
        User("userA", "userA", "https://picsum.photos/id/80/100/100"),
        User("userB", "userB", "https://picsum.photos/id/81/100/100")
    )

    val sampleStoryUsers = sampleUsers.subList(1, sampleUsers.size)

    val sampleExploreFeeds = listOf(
        ExploreFeed(id = "0", imageUrl = "https://picsum.photos/id/200/600/600", user = sampleUsers[1], size = 1),
        ExploreFeed(id = "1", imageUrl = "https://picsum.photos/id/201/600/600", user = sampleUsers[2], size = 2),
        ExploreFeed(id = "2", imageUrl = "https://picsum.photos/id/202/600/600", user = sampleUsers[3], size = 1),
        ExploreFeed(id = "3", imageUrl = "https://picsum.photos/id/203/600/600", user = sampleUsers[4], size = 1),
        ExploreFeed(id = "4", imageUrl = "https://picsum.photos/id/204/600/600", user = sampleUsers[5], size = 2),
        ExploreFeed(id = "5", imageUrl = "https://picsum.photos/id/205/600/600", user = sampleUsers[6], size = 1),
        ExploreFeed(id = "6", imageUrl = "https://picsum.photos/id/206/600/600", user = sampleUsers[7], size = 1),
        ExploreFeed(id = "7", imageUrl = "https://picsum.photos/id/207/600/600", user = sampleUsers[8], size = 2),
        ExploreFeed(id = "8", imageUrl = "https://picsum.photos/id/208/600/600", user = sampleUsers[9], size = 1),
        ExploreFeed(id = "9", imageUrl = "https://picsum.photos/id/209/600/600", user = sampleUsers[10], size = 1),
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