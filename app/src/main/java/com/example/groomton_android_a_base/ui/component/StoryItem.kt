// 파일 경로: app/src/main/java/com.example/groomton_android_a_base/ui/component/StoryItem.kt
package com.example.groomton_android_a_base.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

// ❗ 링(테두리) 및 그라데이션을 위한 Import는 StoryItem 내부에서 사용하지 않으므로 제거합니다. ❗
import androidx.compose.foundation.layout.Box // Box는 계속 사용
// import androidx.compose.foundation.border // 제거
// import androidx.compose.ui.graphics.Brush // 제거
// import androidx.compose.ui.graphics.Color // 제거

import com.example.groomton_android_a_base.model.User


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
// ❗ imageModifier 파라미터를 추가하여, 이미지의 외형(테두리 포함)을 외부에서 제어하도록 합니다. ❗
fun StoryItem(user: User, imageModifier: Modifier = Modifier) {
    // ❗ 그라데이션 색상 정의 및 storyBorderBrush 변수 제거 (외부에서 처리) ❗
    // val gradientColors = listOf(...)
    // val storyBorderBrush = Brush.linearGradient(colors = gradientColors)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            // ❗ Box의 modifier에 imageModifier를 적용하고, 내부에서 size와 clip을 적용합니다. ❗
            // ❗ border는 여기서 제거합니다. ❗
            modifier = imageModifier // 외부에서 전달받은 Modifier
                .size(78.dp) // 전체 크기는 유지 (내부 이미지보다 크게)
                .clip(CircleShape) // 원형으로 자르기
            ,
            contentAlignment = Alignment.Center // 이미지 중앙 정렬
        ) {
            GlideImage(
                model = user.profileImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp) // 이미지는 64dp로 고정
                    .clip(CircleShape) // 이미지도 원형으로 자름
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = user.name, style = MaterialTheme.typography.labelSmall)
    }
}