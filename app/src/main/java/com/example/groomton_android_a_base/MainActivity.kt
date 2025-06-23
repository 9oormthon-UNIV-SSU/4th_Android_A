package com.example.groomton_android_a_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.groomton_android_a_base.ui.theme.GroomTon_Android_A_BaseTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.groomton_android_a_base.dataclass.Comment
import com.example.groomton_android_a_base.screen.ExploreScreen
import com.example.groomton_android_a_base.screen.HomeScreen
import com.example.groomton_android_a_base.screen.ProfileScreen
import com.example.groomton_android_a_base.screen.ReelsScreen
import com.example.groomton_android_a_base.ui.component.BottomBar
import com.example.groomton_android_a_base.ui.theme.GroomTon_Android_A_BaseTheme
import com.example.groomton_android_a_base.dataclass.Feed
import com.example.groomton_android_a_base.dataclass.Story
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import com.example.groomton_android_a_base.dataclass.User

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroomTon_Android_A_BaseTheme {
                NavigationBar(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun NavigationBar(modifier: Modifier = Modifier){
    val navController = rememberNavController()
    val sampleUsers = listOf(
        User(name = "Yeji Kim", id = "user_yeji_123", followers = 1500, followings = 300, Posts = 50, ProfilPictureUrl = "url_yeji", hasUnseenStory = true),
        User(name = "Chris Lee", id = "user_chris_456", followers = 2200, followings = 450, Posts = 120, ProfilPictureUrl = "url_chris", hasUnseenStory = false),
        User(name = "Alex Park", id = "user_alex_789", followers = 800, followings = 150, Posts = 30, ProfilPictureUrl = "url_alex", hasUnseenStory = true)
    )
    val sampleCommentsForFeed1 = listOf(
        Comment(
            id = "comment001",
            user = sampleUsers[1],
            content = "정말 멋진 사진이네요! 👍"
        ),
        Comment(
            id = "comment002",
            user = sampleUsers[2],
            content = "어디인가요? 가보고 싶어요!"
        )
    )

    val sampleStories = listOf(
        Story(id = "story_001", user = sampleUsers[0], imageUrl = "story_img_1", isSeen = false),
        Story(id = "story_002", user = sampleUsers[1], imageUrl = "story_img_2",isSeen = true),
        Story(id = "story_003", user = sampleUsers[2], imageUrl = "story_img_3", isSeen = false),
        Story(id = "story_004", user = sampleUsers[0], imageUrl = "story_img_4", isSeen = false)
    )

    val sampleFeeds = listOf(
        Feed(id = "feed_001", user = sampleUsers[0], imageUrl = "feed_img_1", caption = "첫 번째 피드입니다! #일상", commentCount = 15,
            isLiked = true, likeCount = 100, isBookmarked = false, comments = sampleCommentsForFeed1)

    )

    Scaffold(
        modifier = modifier,
        bottomBar = { BottomBar(navController) },
        content = { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home"){HomeScreen(stories = sampleStories,feeds = sampleFeeds)}
                composable("explore"){ExploreScreen(feeds = SampleDataProvider.sampleExploreFeeds)}
                composable("reels"){ReelsScreen()}
                composable("profile"){ProfileScreen()}
            }
        }
    )
}

@Preview
@Composable
fun NavigationBarPreview() {
    GroomTon_Android_A_BaseTheme {
        NavigationBar(modifier = Modifier.fillMaxSize())
    }
}