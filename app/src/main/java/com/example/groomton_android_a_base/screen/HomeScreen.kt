package com.example.groomton_android_a_base.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groomton_android_a_base.R
import com.example.groomton_android_a_base.dataclass.Feed
import com.example.groomton_android_a_base.dataclass.Story
import com.example.groomton_android_a_base.dataclass.User
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.RequestOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(stories: List<Story>, feeds: List<Feed>, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Icon(
                        painter = painterResource(R.drawable.ic_instagram),
                        contentDescription = null
                    )
                },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_post),
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_like),
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_dm),
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (stories.isNotEmpty()) {
                item {
                    StoriesSection(stories = stories)
                }
                item {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                }
                item {
                    FeedSection(feeds = feeds)
                }
            }
        }
    }
}

@Composable
fun StoriesSection(stories: List<Story>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(stories.size) { index ->
            StoryCard(story = stories[index])
        }
    }
}

@Composable
fun StoryCard(story: Story, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_profile),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )
        Text(text = story.user.name)
    }
}

@Composable
fun FeedSection(feeds: List<Feed>, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        feeds.forEach { feed ->
            FeedCard(feed = feed)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedCard(feed: Feed, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_profile),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Text(feed.user.name,
                    modifier = Modifier.weight(1f))
            }
            Icon(
                painter = painterResource(R.drawable.ic_viewmore_dots),
                contentDescription = null
            )
        }
        GlideImage(
            model = feed.imageUrl,
            contentDescription = null,
            modifier = modifier.fillMaxSize().aspectRatio(1f).background(Color.Gray), // 1ㄷ1 비율
            contentScale = ContentScale.Crop //이미지 비율 맞게 자름
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_like),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_comment),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Text("${feed.commentCount}")
                Icon(
                    painter = painterResource(R.drawable.ic_share),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_bookmark),
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
        }
        Text("Liked by ${feed.user.name} and ${feed.likeCount} others",
            modifier = modifier.padding(8.dp))
        Text(feed.caption,
            modifier = modifier.padding(8.dp))
    }
}




@Preview(showBackground = true, name = "Stories Section Preview")
@Composable
fun StoriesSectionPreview() {
    // --- 예시 User 데이터 생성 ---
    val user1 = User(
        name = "Yeji Kim",
        id = "user_yeji_123",
        followers = 1500,
        followings = 300,
        Posts = 50,
        ProfilPictureUrl = "https://example.com/profile_yeji.jpg", // 실제 이미지 URL로 대체
        hasUnseenStory = true
    )
    val user2 = User(
        name = "Chris Lee",
        id = "user_chris_456",
        followers = 2200,
        followings = 450,
        Posts = 120,
        ProfilPictureUrl = "https://example.com/profile_chris.jpg", // 실제 이미지 URL로 대체
        hasUnseenStory = false // 이 사용자는 본 스토리가 없음
    )
    val user3 = User(
        name = "Alex Park",
        id = "user_alex_789",
        followers = 800,
        followings = 150,
        Posts = 30,
        ProfilPictureUrl = "https://example.com/profile_alex.jpg", // 실제 이미지 URL로 대체
        hasUnseenStory = true
    )
    val user4 = User(
        name = "Minjun Cho",
        id = "user_minjun_000",
        followers = 5000,
        followings = 20,
        Posts = 250,
        ProfilPictureUrl = "https://example.com/profile_minjun.jpg", // 실제 이미지 URL로 대체
        hasUnseenStory = true
    )
    val user6 = User(
        name = "Yeji Kim",
        id = "user_yeji_123",
        followers = 1500,
        followings = 300,
        Posts = 50,
        ProfilPictureUrl = "https://example.com/profile_yeji.jpg", // 실제 이미지 URL로 대체
        hasUnseenStory = true
    )
    val user7 = User(
        name = "Chris Lee",
        id = "user_chris_456",
        followers = 2200,
        followings = 450,
        Posts = 120,
        ProfilPictureUrl = "https://example.com/profile_chris.jpg", // 실제 이미지 URL로 대체
        hasUnseenStory = false // 이 사용자는 본 스토리가 없음
    )
    val sampleStories = listOf(
        Story(
            id = "story_001",
            user = user1,
            imageUrl = "https://example.com/story_image_1.jpg", // 실제 스토리 이미지 URL로 대체

            isSeen = false
        ),
        Story(
            id = "story_002",
            user = user2,
            imageUrl = "https://example.com/story_image_2.jpg",
            isSeen = true // 이 스토리는 이미 봄
        ),
        Story(
            id = "story_003",
            user = user3,
            imageUrl = "https://example.com/story_image_3.jpg",
            isSeen = false
        ),
        Story(
            id = "story_004",
            user = user1, // 같은 유저가 여러 스토리 올릴 수 있음
            imageUrl = "https://example.com/story_image_4.jpg",
            isSeen = false
        ),
        Story(
            id = "story_005",
            user = user4,
            imageUrl = "https://example.com/story_image_5.jpg",
            isSeen = true
        ),
        Story(
            id = "story_001",
            user = user6,
            imageUrl = "https://example.com/story_image_1.jpg", // 실제 스토리 이미지 URL로 대체

            isSeen = false
        ),
        Story(
            id = "story_002",
            user = user7,
            imageUrl = "https://example.com/story_image_2.jpg",
            isSeen = true // 이 스토리는 이미 봄
        )
    )


    StoriesSection(stories = sampleStories)
    // }
}
