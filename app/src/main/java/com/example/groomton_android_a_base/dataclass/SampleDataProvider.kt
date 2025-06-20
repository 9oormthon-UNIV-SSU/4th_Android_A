package com.example.week_06.sampledata

import com.example.week_06.dataclass.Comment
import com.example.week_06.dataclass.ExploreFeed
import com.example.week_06.dataclass.Feed
import com.example.week_06.dataclass.User

object SampleDataProvider {

    val sampleUser1 = User(
        id = "user001", name = "Lina Park", ProfilPictureUrl = "https://picsum.photos/seed/lina/100/100",
        followers = 1250, followings = 300, Posts = 75, hasUnseenStory = true
    )
    val sampleUser2 = User(
        id = "user002", name = "David Kim", ProfilPictureUrl = "https://picsum.photos/seed/david/100/100",
        followers = 880, followings = 150, Posts = 40, hasUnseenStory = false
    )
    val sampleUser3 = User(
        id = "user003", name = "Chloe Lee", ProfilPictureUrl = "https://picsum.photos/seed/chloe/100/100",
        followers = 2500, followings = 500, Posts = 120, hasUnseenStory = true
    )

    val sampleCommentsForFeed1 = listOf(
        Comment("comment001", sampleUser2, "정말 멋진 사진이네요! 👍"),
        Comment("comment002", sampleUser3, "어디인가요? 가보고 싶어요!")
    )
    val sampleCommentsForFeed2 = listOf(
        Comment("comment003", sampleUser1, "색감이 너무 예뻐요 😍")
    )

    val sampleFeed1 = Feed(
        id = "feed001", user = sampleUser1, imageUrl = "https://picsum.photos/seed/original_feed1/600/800",
        likeCount = 152, caption = "오늘의 하늘. 구름 한 점 없이 맑아서 기분이 좋네요! ☀️ #하늘 #풍경 #일상",
        commentCount = 2, isBookmarked = false, isLiked = true,
        comments = sampleCommentsForFeed1
    )
    val sampleFeed2 = Feed(
        id = "feed002", user = sampleUser2, imageUrl = "https://picsum.photos/seed/original_feed2/600/600",
        likeCount = 89, caption = "집에서 만든 맛있는 파스타! 🍝 #요리 #홈쿡 #파스타 #jmt",
        commentCount = 1, isBookmarked = true, isLiked = false,
        comments = sampleCommentsForFeed2
    )
    val sampleFeed3 = Feed(
        id = "feed003", user = sampleUser3, imageUrl = "https://picsum.photos/seed/original_feed3/800/600",
        likeCount = 312, caption = "오랜만에 친구들과 즐거운 시간! 😊 #친구 #주말 #행복",
        commentCount = 0, isBookmarked = false, isLiked = true,
        comments = emptyList()
    )

    val sampleExploreFeeds: List<ExploreFeed> = listOf(
        ExploreFeed(
            user = sampleUser1,
            imageUrl = "https://picsum.photos/seed/exp1_user1/400/600",
            size = 2,
            feed = sampleFeed1
        ),
        ExploreFeed(
            user = sampleUser2,
            imageUrl = "https://picsum.photos/seed/exp2_user2/300/300",
            size = 1,
            feed = sampleFeed2
        ),
        ExploreFeed(
            user = sampleUser3,
            imageUrl = "https://picsum.photos/seed/exp3_user3/500/350",
            size = 1,
            feed = sampleFeed3
        ),
        ExploreFeed(
            user = sampleUser1,
            imageUrl = "https://picsum.photos/seed/exp4_user1_alt/300/500",
            size = 2,
            feed = sampleFeed1
        ),
        ExploreFeed(
            user = sampleUser2,
            imageUrl = "https://picsum.photos/seed/exp5_user2_alt/700/500",
            size = 2,
            feed = sampleFeed2
        ),
        ExploreFeed(
            user = sampleUser1,
            imageUrl = "https://picsum.photos/seed/exp6_user1_new/250/250",
            size = 1,
            feed = sampleFeed1 // sampleFeed1을 다른 ExploreFeed에서도 사용
        ),
        ExploreFeed(
            user = sampleUser3,
            imageUrl = "https://picsum.photos/seed/exp7_user3_new/600/800",
            size = 2,
            feed = sampleFeed3
        ),
        ExploreFeed(
            user = sampleUser2,
            imageUrl = "https://picsum.photos/seed/exp8_user2_another/320/320",
            size = 1,
            feed = sampleFeed2
        )
        // 필요에 따라 더 많은 ExploreFeed 샘플 추가
    )

    val allSampleFeeds = listOf(sampleFeed1, sampleFeed2, sampleFeed3) // 이건 Feed 리스트
}