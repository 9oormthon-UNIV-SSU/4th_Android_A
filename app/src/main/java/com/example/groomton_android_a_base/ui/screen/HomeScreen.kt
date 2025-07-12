package com.example.groomton_android_a_base.ui.screen

import PostCard // PostCard 컴포저블의 정확한 import 경로 확인 (예: com.foo.instagram.ui.component.PostCard)
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
// Scaffold는 MainActivity에서 사용하므로 여기서는 제거합니다.
// import androidx.compose.material3.Scaffold // <<< 이 줄을 삭제합니다.

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.groomton_android_a_base.ui.component.StorySection // StorySection 컴포저블 import
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

// Divider 관련 import
import androidx.compose.material3.Divider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth

// TopAppBarScrollBehavior 관련 import
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.ui.input.nestedscroll.nestedScroll // nestedScroll Modifier import
import androidx.compose.foundation.layout.PaddingValues // innerPadding 타입을 명확히 하기 위해
import androidx.compose.material3.ExperimentalMaterial3Api


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = viewModel(),
    // MainActivity의 Scaffold에서 전달받을 innerPadding과 scrollBehavior 파라미터 추가
    innerPadding: PaddingValues, // <<< 이 파라미터 추가
    scrollBehavior: TopAppBarScrollBehavior // <<< 이 파라미터 추가
) {
    val posts = viewModel.postList
    val stories = List(6) { "user$it" } // 예시용 스토리 데이터 (실제 데이터 구조에 맞게 변경 권장)


    // HomeScreen 내부의 Scaffold를 제거합니다.
    // Scaffold { innerPadding -> // <<< 이 줄과 아래 닫는 괄호 }를 삭제합니다.
    LazyColumn(
        // MainActivity에서 받은 innerPadding을 여기에 적용합니다.
        modifier = modifier
            .fillMaxSize()
            // LazyColumn의 스크롤 이벤트를 TopAppBarScrollBehavior로 전달
            .nestedScroll(scrollBehavior.nestedScrollConnection) // <<< 이 부분 추가

    ) {

        // 1. 스토리 섹션
        item {
            StorySection(stories = stories)
        }

        // 2. 스토리 섹션과 게시물 섹션 사이에 구분선 추가
        item {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp), // 좌우 패딩을 조절하여 선의 길이를 결정 (0으로 하면 화면 끝까지)
                thickness = 3.dp, // 선의 두께 (얇은 선)
                color = Color.LightGray // 선의 색상 (회색 계열)
                // MaterialTheme.colorScheme.outlineVariant 도 좋은 옵션입니다.
            )
        }

        // 3. 게시물 섹션
        items(posts) { post ->
            PostCard(
                post = post,
                onLikeClick = { viewModel.toggleLike(post.id) }
            )
        }
    }
    // } // <<< 이 닫는 괄호 } 도 삭제합니다.
}