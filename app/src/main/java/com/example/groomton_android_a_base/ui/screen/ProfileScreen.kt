package com.example.groomton_android_a_base.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import androidx.compose.foundation.ExperimentalFoundationApi

// Material3 컴포넌트들
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider

// 아이콘 리소스
import androidx.compose.ui.res.painterResource
import com.example.groomton_android_a_base.R

// 새로운 요소들을 위한 import
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.groomton_android_a_base.ui.component.StoryItem
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import com.example.groomton_android_a_base.ui.component.StoryItem
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.painterResource // ❗ painterResource를 위해 필요합니다. ❗



@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: String,
    viewModel: FeedViewModel,
    navController: NavController
) {
    val userPosts = viewModel.postList.filter { it.user.id == userId }
    val user = userPosts.firstOrNull()?.user // user는 여전히 null일 수 있음


    val highlightUsers = remember { SampleDataProvider.sampleHighlightUsers }
    val currentLoggedInUserId = SampleDataProvider.sampleUsers.first { it.id == "my_user" }.id


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0),
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // ❗ user가 null일 수 있으므로 안전 호출 ?.name 사용 ❗
                        Text(text = user?.name ?: "프로필", style = MaterialTheme.typography.titleLarge)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "Back")
                    }
                },
                actions = {

                    IconButton(onClick = { /* TODO: 추가 */ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_dots), contentDescription = "Add")
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. 프로필 헤더 섹션
            if (user != null) { // user가 null이 아닐 때만 프로필 정보를 그립니다.
                val nonNullUser = user // ❗ user가 null이 아님을 명시적으로 새로운 변수에 할당 ❗
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GlideImage(
                            model = nonNullUser.profileImageUrl, // ❗ nonNullUser 사용 ❗
                            contentDescription = null,
                            modifier = Modifier.size(80.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceAround) {
                            ProfileStat(count = "1,132", label = "게시물")
                            ProfileStat(count = "60K", label = "팔로워")
                            ProfileStat(count = "4", label = "팔로잉")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = nonNullUser.name, style = MaterialTheme.typography.titleMedium) // ❗ nonNullUser 사용 ❗
                    Text(text = "Find High Quality HD Pictures.", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "www.wallpapers4k.com", style = MaterialTheme.typography.bodySmall, color = Color.Blue)
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 0.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (userId == currentLoggedInUserId) { // 내 프로필일 경우
                            OutlinedButton(
                                onClick = { /* TODO: 프로필 편집 */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                )
                            ) {
                                Text("프로필 편집")
                            }
                            OutlinedButton(
                                onClick = { /* TODO: 프로필 공유 */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                )
                            ) {
                                Text("프로필 공유")
                            }
                        } else { // 상대방 프로필일 경우
                            Button( // 팔로우 버튼 (채워진 파란색)
                                onClick = { /* TODO: 팔로우 */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary, // 테마의 파란색
                                    contentColor = Color.White
                                )
                            ) {
                                // ❗ Text("팔로우") 대신 Icon 사용 ❗
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_followbutton), // ❗ ic_followButton 사용 ❗
                                    contentDescription = "Follow"
                                )
                            }
                            OutlinedButton( // 메시지 버튼 (테두리)
                                onClick = { /* TODO: 메시지 */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                )
                            ) {
                                // ❗ Text("메시지") 대신 Icon 사용 ❗
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_messagebutton), // ❗ ic_messageButton 사용 ❗
                                    contentDescription = "Message"
                                )
                            }
                        }
                    }
                }
            } else {
                // user 정보 로딩 중이거나 찾을 수 없을 때의 대체 UI
                Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                    Text("사용자 정보를 찾을 수 없습니다.", color = Color.Gray)
                    // 또는 CircularProgressIndicator()
                }
            }


            // 2. 하이라이트/스토리 아이템 (LazyRow) - 이것은 userPosts가 비어있어도 항상 보여야 합니다.
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(highlightUsers) { highlightUser ->
                    StoryItem(user = highlightUser)
                }
            }
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.LightGray)

            // 3. 프로필 탭 (게시물, 릴스, 태그됨)
            Row(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* TODO: 게시물 탭 */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_grid_tab), contentDescription = "Posts Grid")
                }
                IconButton(onClick = { /* TODO: 릴스 탭 */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_reels), contentDescription = "Reels")
                }
                IconButton(onClick = { /* TODO: 태그됨 탭 */ }) {
                    Icon(painter = painterResource(id = R.drawable.ic_profile), contentDescription = "Tagged Posts")
                }
            }
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.LightGray)

            // 4. 게시물 그리드
            if (userPosts.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(painter = painterResource(id = R.drawable.ic_logos_instagram), contentDescription = "Camera", modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("게시물 없음")
                    }
                }
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize().padding(horizontal = 1.dp),
                    verticalItemSpacing = 1.dp,
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                ) {
                    items(userPosts) { post ->
                        GlideImage(
                            model = post.postImageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable

fun ProfileStat(count: String, label: String) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = count, style = MaterialTheme.typography.titleLarge)

        Text(text = label, style = MaterialTheme.typography.bodySmall)

    }

}