package com.example.groomton_android_a_base.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groomton_android_a_base.R


@Composable
fun StorySection(stories: List<String>) {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(stories) { name ->
            StoryItem(username = name, imageRes = R.drawable.ic_profile)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}