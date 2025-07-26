package com.example.groomton_android_a_base.ui.component

import androidx.compose.foundation.layout.Column // StoryItem 클릭 가능한 영역을 Column으로 감싸기 위해 추가
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height // StoryItem의 Text 아래 Spacer를 위해
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme // StoryItem의 Text 스타일을 위해
import androidx.compose.material3.Text // StoryItem의 Text를 위해
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.gestures.ScrollableDefaults

import com.example.groomton_android_a_base.model.User
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.clickable // ❗ StoryItem 클릭을 위해 필요 ❗
import androidx.compose.ui.Alignment
import androidx.navigation.NavController // ❗ NavController를 위해 추가 ❗


@OptIn(ExperimentalFoundationApi::class)
@Composable
// ❗ navController 파라미터를 추가합니다. ❗
fun StorySection(users: List<User>, storyItemImageModifier: Modifier = Modifier, navController: NavController) {
    val listState = rememberLazyListState()
    val flingBehavior = ScrollableDefaults.flingBehavior()

    LazyRow(
        state = listState,
        modifier = Modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        flingBehavior = flingBehavior
    ) {
        items(users) { user ->
            // StoryItem 자체를 클릭 가능하게 만들고, 그 안에 Text를 포함하여 전체를 클릭 영역으로 만듭니다.
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                // ❗ Column에 clickable Modifier를 적용하여 StoryItem 전체를 클릭 가능하게 ❗
                modifier = Modifier.clickable {
                    navController.navigate("story_detail/${user.id}")
                }
            ) {
                // StoryItem은 이제 imageModifier만 받습니다.
                StoryItem(user = user, imageModifier = storyItemImageModifier)
                Spacer(modifier = Modifier.height(4.dp)) // 이미지와 텍스트 사이 간격
                //Text(text = user.name, style = MaterialTheme.typography.labelSmall) // 사용자 이름 텍스트
            }
        }
    }
}