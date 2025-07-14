package com.example.groomton_android_a_base.sampledata

import com.example.groomton_android_a_base.model.Comment
import com.example.groomton_android_a_base.model.ExploreFeed
import com.example.groomton_android_a_base.model.Feed
import com.example.groomton_android_a_base.model.Story
import com.example.groomton_android_a_base.model.User

object SampleDataProvider {

    val sampleUsers = listOf(
        User(
            id = "user001", name = "Lina Park", ProfilPictureUrl = "https://picsum.photos/seed/lina/100/100",
            followers = 1250, followings = 300, Posts = 75, hasStory = false, hasUnseenStory = false,
            story = Story(
                storyId = "story001",
                imageUrl = "https://picsum.photos/seed/story_lina/720/1280"
            )
        ),
        User(
            id = "user002", name = "David Kim", ProfilPictureUrl = "https://picsum.photos/seed/david/100/100",
            followers = 880, followings = 150, Posts = 40, hasStory = true, hasUnseenStory = true,
            story = Story(
                storyId = "story002",
                imageUrl = "https://picsum.photos/seed/story_david/720/1280"
            )
        ),
        User(
            id = "user003", name = "Chloe Lee", ProfilPictureUrl = "https://picsum.photos/seed/chloe/100/100",
            followers = 2500, followings = 500, Posts = 120, hasStory = false, hasUnseenStory = false,
            story = Story(
                storyId = "story003",
                imageUrl = "https://picsum.photos/seed/story_james/720/1280"
            )
        ),
        User(
            id = "user004", name = "James Oh", ProfilPictureUrl = "https://picsum.photos/seed/james/100/100",
            followers = 720, followings = 210, Posts = 55, hasStory = true, hasUnseenStory = true,
            story = Story(
                storyId = "story004",
                imageUrl = "https://picsum.photos/seed/story_sophia/720/1280"
            )
        ),
        User(
            id = "user005", name = "Sophia Kang", ProfilPictureUrl = "https://picsum.photos/seed/sophia/100/100",
            followers = 1800, followings = 450, Posts = 90, hasStory = true, hasUnseenStory = true,
            story = Story(
                storyId = "story005",
                imageUrl = "https://picsum.photos/seed/story_minjun/720/1280"
            )
        ),
         User(
            id = "user006", name = "Minjun Choi", ProfilPictureUrl = "https://picsum.photos/seed/minjun/100/100",
            followers = 950, followings = 180, Posts = 60, hasStory = true, hasUnseenStory = true,
             story = Story(
                 storyId = "story006",
                 imageUrl = "https://picsum.photos/seed/story_alice/720/1280"
             )
        ),
        User(
        id = "user007", name = "Alice Brown", ProfilPictureUrl = "https://picsum.photos/seed/alice/100/100",
        followers = 1100, followings = 320, Posts = 70, hasStory = true, hasUnseenStory = true,
            story = Story(
                storyId = "story007",
                imageUrl = "https://picsum.photos/seed/story_my/720/1280"
            )
        )
    )

    val sampleCommentsForFeed1 = listOf(
        Comment("comment001", sampleUsers[2], "정말 멋진 사진이네요! 👍"),
        Comment("comment002", sampleUsers[3], "어디인가요? 가보고 싶어요!")
    )
    val sampleCommentsForFeed2 = listOf(
        Comment("comment003", sampleUsers[1], "색감이 너무 예뻐요 😍")
    )

    val sampleFeed1 = Feed(
        id = "feed001", user = sampleUsers[1], imageUrl = "https://picsum.photos/seed/original_feed1/600/800",
        likeCount = 152, caption = "오늘의 하늘. 구름 한 점 없이 맑아서 기분이 좋네요! ☀️ #하늘 #풍경 #일상",
        commentCount = 2, isBookmarked = false, isLiked = false,
        comments = sampleCommentsForFeed1, isreels = false
    )
    val sampleFeed2 = Feed(
        id = "feed002", user = sampleUsers[2], imageUrl = "https://picsum.photos/seed/original_feed2/600/600",
        likeCount = 89, caption = "집에서 만든 맛있는 파스타! 🍝 #요리 #홈쿡 #파스타 #jmt",
        commentCount = 1, isBookmarked = false, isLiked = false,
        comments = sampleCommentsForFeed2, isreels = false
    )
    val sampleFeed3 = Feed(
        id = "feed003", user = sampleUsers[3], imageUrl = "https://picsum.photos/seed/original_feed3/800/600",
        likeCount = 312, caption = "오랜만에 친구들과 즐거운 시간! 😊 #친구 #주말 #행복",
        commentCount = 0, isBookmarked = false, isLiked = true,
        comments = emptyList(), isreels = false
    )

    val sampleExploreFeeds: List<ExploreFeed> = listOf(
        ExploreFeed(
            user = sampleUsers[0],
            imageUrl = "https://picsum.photos/seed/exp1_user1/400/600",
            size = 2,
            feed = sampleFeed1
        ),
        ExploreFeed(
            user = sampleUsers[2],
            imageUrl = "https://picsum.photos/seed/exp2_user2/300/300",
            size = 1,
            feed = sampleFeed2
        ),
        ExploreFeed(
            user = sampleUsers[3],
            imageUrl = "https://picsum.photos/seed/exp3_user3/500/350",
            size = 1,
            feed = sampleFeed3
        ),
        ExploreFeed(
            user = sampleUsers[1],
            imageUrl = "https://picsum.photos/seed/exp4_user1_alt/300/500",
            size = 2,
            feed = sampleFeed1
        ),
        ExploreFeed(
            user = sampleUsers[2],
            imageUrl = "https://picsum.photos/seed/exp5_user2_alt/700/500",
            size = 2,
            feed = sampleFeed2
        ),
        ExploreFeed(
            user = sampleUsers[3],
            imageUrl = "https://picsum.photos/seed/exp6_user1_new/250/250",
            size = 1,
            feed = sampleFeed1 // sampleFeed1을 다른 ExploreFeed에서도 사용
        ),
        ExploreFeed(
            user = sampleUsers[4],
            imageUrl = "https://picsum.photos/seed/exp7_user3_new/600/800",
            size = 2,
            feed = sampleFeed3
        ),
        ExploreFeed(
            user = sampleUsers[5],
            imageUrl = "https://picsum.photos/seed/exp8_user2_another/320/320",
            size = 1,
            feed = sampleFeed2
        )
    )

    val allSampleFeeds = listOf(sampleFeed1, sampleFeed2, sampleFeed3) // 이건 Feed 리스트
}