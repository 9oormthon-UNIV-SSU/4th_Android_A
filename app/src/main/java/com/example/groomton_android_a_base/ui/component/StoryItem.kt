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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.groomton_android_a_base.model.User


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun StoryItem(user: User) {
    val gradientColors = listOf(
        Color(0xFFFEDA75),
        Color(0xFFFA7E1E),
        Color(0xFFD62976),
        Color(0xFF962FBF),
        Color(0xFF4F5BD5)
    )
    val storyBorderBrush = Brush.linearGradient(colors = gradientColors)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(78.dp)
                .clip(CircleShape)
                .border(4.dp, storyBorderBrush, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = user.profileImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = user.name, style = MaterialTheme.typography.labelSmall)
    }
}