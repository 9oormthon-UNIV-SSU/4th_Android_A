package com.example.groomton_android_a_base.ui.component

// ... (기존 import들) ...
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.rememberLazyListState

// ❗ ScrollableDefaults 임포트 추가 ❗
import androidx.compose.foundation.gestures.ScrollableDefaults
import com.example.groomton_android_a_base.model.User
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StorySection(users: List<User>, storyItemImageModifier: Modifier = Modifier) {
    val listState = rememberLazyListState()

    val flingBehavior = ScrollableDefaults.flingBehavior()

    LazyRow(
        state = listState,
        modifier = Modifier
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        flingBehavior = flingBehavior
    ) {
        items(users) { user ->
            StoryItem(user = user, imageModifier = storyItemImageModifier)
        }
    }
}