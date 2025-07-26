package com.example.groomton_android_a_base.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.res.painterResource

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.groomton_android_a_base.R
import com.example.groomton_android_a_base.model.Post
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import com.example.groomton_android_a_base.viewmodel.FeedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


@OptIn(ExperimentalComposeUiApi::class, ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StoryDetailScreen(
    initialUserId: String,
    viewModel: FeedViewModel,
    navController: NavController
) {
    val allStoryUsers = remember { SampleDataProvider.sampleUsers } // sampleUsers로 변경 (전체 사용자 리스트)
    val initialUserIndex = remember(initialUserId) {
        allStoryUsers.indexOfFirst { it.id == initialUserId }.coerceAtLeast(0)
    }

    var currentUserIndex by rememberSaveable { mutableStateOf(initialUserIndex) }

    val currentUsersPosts = remember(currentUserIndex) {
        val currentUserId = allStoryUsers[currentUserIndex].id
        viewModel.postList.filter { it.user.id == currentUserId }
    }

    var currentPostIndex by remember { mutableStateOf(0) }
    var progress by remember { mutableStateOf(0.0f) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = (3000 * (1.0f - progress)).toInt(), easing = LinearEasing),
        label = "storyProgressAnimation"
    )

    LaunchedEffect(currentUserIndex, currentPostIndex, currentUsersPosts.size) {
        if (currentUsersPosts.isNotEmpty() && isActive) {
            progress = 0.0f
            val segmentDuration = 3000L
            val totalSegments = currentUsersPosts.size.toFloat() // Float으로 명시
            val progressPerSegment = 1.0f / totalSegments

            val currentSegmentStartProgress = currentPostIndex * progressPerSegment

            // 스토리 자동 넘김 및 진행 바 로직
            while (isActive) { // progress < 1.0f && isActive 제거 (진행 로직 안에서 판단)
                val startTime = System.currentTimeMillis()
                var elapsedTime = 0L
                progress = currentSegmentStartProgress // 각 세그먼트 시작 시 진행도 초기화 (애니메이션 시작점)

                while (elapsedTime < segmentDuration && isActive) {
                    delay(10)
                    elapsedTime = System.currentTimeMillis() - startTime
                    // 현재 게시물 조각 내에서의 진행률 계산 후 전체 진행률에 합산
                    progress = currentSegmentStartProgress + (elapsedTime.toFloat() / segmentDuration) * progressPerSegment
                }

                if (isActive) {
                    if (currentPostIndex < currentUsersPosts.size - 1) {
                        currentPostIndex++
                    } else {
                        if (currentUserIndex < allStoryUsers.size - 1) {
                            currentUserIndex++
                            currentPostIndex = 0
                        } else {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }

    val tapToAdvanceStory: (Alignment.Horizontal) -> Unit = { alignment ->
        if (alignment == Alignment.Start) {
            if (currentPostIndex > 0) {
                currentPostIndex--
            } else if (currentUserIndex > 0) {
                currentUserIndex--
                currentPostIndex = currentUsersPosts.size -1 // 이전 사용자 스토리의 마지막 게시물부터 시작
            } else {
                navController.popBackStack()
            }
        } else {
            if (currentPostIndex < currentUsersPosts.size - 1) {
                currentPostIndex++
            } else if (currentUserIndex < allStoryUsers.size - 1) {
                currentUserIndex++
                currentPostIndex = 0
            } else {
                navController.popBackStack()
            }
        }
    }

    // UI 구성
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (currentUsersPosts.isEmpty() || currentPostIndex >= currentUsersPosts.size) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("게시물 없음 또는 로딩 중...", color = Color.White)
                CircularProgressIndicator()
            }
        } else {
            val postToShow = currentUsersPosts[currentPostIndex]

            GlideImage(
                model = postToShow.postImageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            // 상단 진행 바
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // ❗ top 패딩을 고정된 Dp 값으로 명시 ❗
                    .padding(vertical = 40.dp, horizontal = 8.dp) // 24+4+16=44dp
                    .height(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                currentUsersPosts.forEachIndexed { index, _ ->
                    val segmentProgress = when {
                        index < currentPostIndex -> 1.0f
                        index == currentPostIndex -> animatedProgress
                        else -> 0.0f
                    }
                    LinearProgressIndicator(
                        progress = segmentProgress,
                        modifier = Modifier.weight(1f).clip(MaterialTheme.shapes.small),
                        color = Color.White,
                        trackColor = Color.White.copy(alpha = 0.5f)
                    )
                }
            }

            // 사용자 정보 (프로필 이미지, 이름, 뒤로가기 버튼)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // ❗ top 패딩을 고정된 Dp 값으로 명시 ❗
                    .padding(top = (44 + 8).dp, start = 16.dp, end = 16.dp), // 44dp + 8dp (진행바 아래 간격)
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                GlideImage(
                    model = postToShow.user.profileImageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = postToShow.user.name, color = Color.White)
            }

            // 좌우 터치 영역
            Row(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { tapToAdvanceStory(Alignment.Start) })
                Box(modifier = Modifier.weight(1f).fillMaxHeight().clickable { tapToAdvanceStory(Alignment.End) })
            }
        }
    }
}