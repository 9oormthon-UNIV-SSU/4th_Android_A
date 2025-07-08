package com.example.groomton_android_a_base.ui.component.homescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.groomton_android_a_base.model.User
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import com.example.groomton_android_a_base.ui.theme.instagramGradientColors

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProfileIcon(
    user : User,
    modifier: Modifier = Modifier,
    iconSize: Dp = 80.dp,
    storyBorderWidth: Dp = 4.dp,
    storyBorderPadding: Dp = 5.dp // 아이콘과 스토리 링 사이의 간격
    ) {
    val instagramGradient = Brush.sweepGradient(instagramGradientColors)
    val seenStoryColor = Color.LightGray

    Box(
        modifier = modifier
            .size(iconSize)
            .let {
                if (user.hasUnseenStory) {
                    it.border(
                        width = storyBorderWidth,
                        brush = instagramGradient,
                        shape = CircleShape
                    )
                } else {
                    it.border(
                        width = storyBorderWidth,
                        color = seenStoryColor,
                        shape = CircleShape
                    )
                }
            }
            .padding(
                if (user.hasUnseenStory)
                    storyBorderPadding else 0.dp
            ),
        contentAlignment = Alignment.Center
    ){
        GlideImage(
            model = user.ProfilPictureUrl,
            contentDescription = "${user.name}'s story",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .fillMaxSize()
                .background(Color.Gray), // 로딩 중 배경 및 원형 모양
            contentScale = ContentScale.Crop,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileIconPreview(){
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileIcon(user = SampleDataProvider.sampleUsers[0])
        ProfileIcon(user = SampleDataProvider.sampleUsers[1])
    }
}