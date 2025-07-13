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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groomton_android_a_base.R
import com.example.groomton_android_a_base.model.Feed
import com.example.groomton_android_a_base.model.Story
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.groomton_android_a_base.sampledata.SampleDataProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(stories: List<Story>, feeds: List<Feed>, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = modifier.padding(start = 10.dp, end = 5.dp, top = 5.dp),
                title = {
                    Icon(
                        painter = painterResource(R.drawable.ic_instagram),
                        contentDescription = null
                    )
                },
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
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
                },
                scrollBehavior = scrollBehavior
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
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(stories.size) { index ->
            StoryCard(story = stories[index])
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StoryCard(story: Story, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {},
            modifier = Modifier.size(80.dp)
        ) {
            GlideImage(
                model = story.user.ProfilPictureUrl,
                contentDescription = "${story.user.name}'s story",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .fillMaxSize().background(Color.Gray), // 로딩 중 배경 및 원형 모양
                contentScale = ContentScale.Crop,
            )
        }
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
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                IconButton(
                    onClick = {}
                ) {
                    GlideImage( // 예시: GlideImage 사용
                        model = feed.user.ProfilPictureUrl, // 실제 프로필 이미지 URL
                        contentDescription = "${feed.user.name}'s story",
                        modifier = Modifier
                            .size(64.dp) // 적절한 크기 지정
                            .clip(CircleShape).background(Color.Gray), // 로딩 중 배경 및 원형 모양
                        contentScale = ContentScale.Crop
                    )
                }
                Text(text = feed.user.name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically,
                        )
                )
            }
            IconButton( onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_viewmore_dots),
                    contentDescription = null
                )
            }
        }
        GlideImage(
            model = feed.imageUrl,
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .background(Color.Gray), // 1ㄷ1 비율
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
                IconButton(onClick = {})  {
                    Icon(
                        painter = painterResource(R.drawable.ic_like),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                IconButton(onClick = {})  {
                    Icon(
                        painter = painterResource(R.drawable.ic_comment),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Text("${feed.commentCount}")
                IconButton(onClick = {})  {
                    Icon(
                        painter = painterResource(R.drawable.ic_share),
                        contentDescription = null,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            IconButton(onClick = {})  {
                Icon(
                    painter = painterResource(R.drawable.ic_bookmark),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Text("Liked by ${feed.user.name} and ${feed.likeCount} others",
            modifier = modifier.padding(8.dp))
        Text(feed.caption,
            modifier = modifier.padding(8.dp))
    }
}




@Preview(showBackground = true, name = "Home Screen Preview")
@Composable
fun HomeScreenPreview() {
    HomeScreen(stories = SampleDataProvider.sampleStories, feeds = SampleDataProvider.allSampleFeeds)
}
