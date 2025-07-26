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
import androidx.compose.material3.MaterialTheme

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun ProfileScreen(
    userId: String,
    viewModel: FeedViewModel,
    navController: NavController
) {
    // ❗ ViewModel에서 해당 유저의 게시물만 필터링 ❗
    val userPosts = viewModel.postList.filter { it.user.id == userId }
    val user = userPosts.firstOrNull()?.user ?: return

    Column(modifier = Modifier.fillMaxSize()) {
        // 프로필 정보 영역
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = user.profileImageUrl,
                contentDescription = null,
                modifier = Modifier.size(80.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
        }

        // 게시물 그리드
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