package com.example.groomton_android_a_base.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Material 3 관련
import androidx.compose.material3.Divider
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ExperimentalMaterial3Api // TopAppBar, Scaffold 등 사용을 위해
import androidx.compose.material3.TopAppBarScrollBehavior // 스크롤 동작을 위해
import androidx.compose.material3.rememberTopAppBarState // scrollBehavior 생성을 위해
import androidx.compose.material3.TopAppBarDefaults // scrollBehavior 생성을 위해
import androidx.compose.material3.Scaffold // ❗ HomeScreen 내부에 Scaffold 추가 ❗
import androidx.compose.material3.TopAppBar // ❗ HomeScreen 내부에 TopAppBar 추가 ❗
import androidx.compose.material3.Icon // TopAppBar Icon을 위해
import androidx.compose.material3.IconButton // TopAppBar IconButton을 위해
import androidx.compose.material3.MaterialTheme // TopAppBar 색상 등을 위해
import androidx.compose.material3.Text // TopAppBar Text를 위해
import com.example.groomton_android_a_base.ui.theme.InstagramTheme

// 레이아웃 관련
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues // innerPadding 타입
import androidx.compose.ui.input.nestedscroll.nestedScroll // nestedScroll Modifier
import androidx.compose.foundation.clickable // PostCard 클릭을 위해
import androidx.compose.foundation.layout.Row // TopAppBar Title/Actions용
import androidx.compose.foundation.layout.Arrangement // TopAppBar Title/Actions용
import androidx.compose.foundation.layout.WindowInsets // TopAppBar의 windowInsets용
import androidx.compose.ui.res.painterResource // TopAppBar 아이콘용
import androidx.compose.ui.unit.dp // dp 단위
import androidx.compose.ui.Alignment

// 프로젝트 컴포넌트 및 데이터
import com.example.groomton_android_a_base.ui.component.PostCard
import com.example.groomton_android_a_base.ui.component.StorySection
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import com.example.groomton_android_a_base.R // 앱 리소스 (아이콘)
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel,
    innerPadding: PaddingValues,
    navController: NavController
) {
    val posts = viewModel.postList
    val storyUsers = SampleDataProvider.sampleStoryUsers

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                modifier = Modifier.padding(0.dp),
                windowInsets = WindowInsets(0),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logos_instagram),
                            contentDescription = "Instagram Logo",
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {/*좋아요 클릭*/}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart_outline),
                            contentDescription = "Likes",
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                    IconButton(onClick = {/*메시지 클릭*/}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_message_icon),
                            contentDescription = "Messages",
                            modifier = Modifier.padding(0.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { currentScreenInnerPadding ->
        LazyColumn(
            // ❗ Scaffold가 제공하는 패딩을 올바르게 적용합니다. ❗
            modifier = Modifier
                .fillMaxSize()
                .padding(currentScreenInnerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            // ❗ TopAppBar와 스토리 섹션 사이의 Divider를 제거합니다. ❗
            item { StorySection(users = storyUsers) }
            item {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
            items(posts) { post ->
                PostCard(
                    post = post,
                    onLikeClick = { viewModel.toggleLike(post.id) },
                    onUserClick = { user ->
                        navController.navigate("profile/${user.id}")
                    },
                    modifier = Modifier
                )
            }
        }
    }
}