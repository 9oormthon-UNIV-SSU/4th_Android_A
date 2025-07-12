package com.example.groomton_android_a_base.ui.component

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size     // <-- 이거!
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

@Composable
fun StoryItem(username: String, imageRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = username, style = MaterialTheme.typography.labelSmall)
    }
//        Divider(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 0.dp), // 선의 좌우 패딩 (0으로 하면 화면 끝까지)
//            thickness = 0.5.dp, // 선의 두께 (얇은 선)
//            color = Color.LightGray // 선의 색상 (회색 계열)
//            // MaterialTheme.colorScheme.outlineVariant 도 좋은 옵션입니다.
//        )

}