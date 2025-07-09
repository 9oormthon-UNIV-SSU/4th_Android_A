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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groomton_android_a_base.R
import com.example.groomton_android_a_base.model.Feed
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.ui.component.homescreen.ProfileIcon
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import com.example.groomton_android_a_base.viewmodel.UserViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = modifier.padding(start = 10.dp, end = 5.dp),
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
            item {
                StoriesSection()
            }
            item {
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
            item {
                FeedSection()
            }
        }
    }
}

@Composable
fun StoriesSection(modifier: Modifier = Modifier) {
    val userViewModel: UserViewModel = hiltViewModel()
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        var users = userViewModel.userList
        items(users.size) { index ->
            if (users[index].hasStory)
                StoryCard(user = users[index], userViewModel= userViewModel)
        }
    }
}

@Composable
fun StoryCard(user: User, userViewModel: UserViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {userViewModel.toggleUserStoryState(user.id)},
            modifier = Modifier.size(80.dp)
        ) {
            ProfileIcon(user = user)
        }
        Text(text = user.name)
    }
}

@Composable
fun FeedSection(modifier: Modifier = Modifier) {
    val feedViewModel: FeedViewModel = hiltViewModel()
    val feeds = feedViewModel.feedList

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        feeds.forEach { feed ->
            FeedCard(feed = feed,feedViewModel= feedViewModel)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FeedCard(feed: Feed,feedViewModel: FeedViewModel, modifier: Modifier = Modifier) {
    val userViewModel : UserViewModel = hiltViewModel()

    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            IconButton(
                onClick = {userViewModel.toggleUserStoryState(feed.user.id)}
            ) {
                ProfileIcon(feed.user)
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
            IconButton(onClick = {feedViewModel.toggleLike(feed.id)})  {
                Icon(
                    if (feed.isLiked) painterResource(R.drawable.ic_filled_like)
                    else painterResource(R.drawable.ic_like),
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = if(feed.isLiked) Color.Unspecified
                    else LocalContentColor.current
                )
            }
            Text("${feed.likeCount}")
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
        IconButton(onClick = { feedViewModel.toggleBookmark(feed.id)

        })  {
            Icon(
                if (feed.isBookmarked) painterResource(R.drawable.ic_filled_bookmark)
                else painterResource(R.drawable.ic_bookmark),
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




@Preview(showBackground = true, name = "Home Screen Preview")
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
