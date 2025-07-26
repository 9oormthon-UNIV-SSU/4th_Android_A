// 파일 경로: app/src/main/java/com.example/groomton_android_a_base/ui/component/SearchBar.kt
package com.example.groomton_android_a_base.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.res.painterResource
import com.example.groomton_android_a_base.R

import androidx.compose.material3.SearchBar as M3SearchBar // ❗ M3SearchBar 별칭 import ❗
import androidx.compose.ui.graphics.Color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var active by remember { mutableStateOf(false) }

    // ❗ M3SearchBar는 @Composable 함수 내부에 올바르게 호출됩니다. ❗
    M3SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        placeholder = { Text("Search", fontSize = 14.sp) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_explore),
                contentDescription = "Search Icon"
            )
        },
        active = active,
        onActiveChange = { newActiveState ->
            active = newActiveState
        },
        onSearch = { newQuery ->
            active = false
        },
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFE8E8E8),
        ),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp, max = 300.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("최근 검색 화면입니다", color = Color.Gray)
        }
    }
}