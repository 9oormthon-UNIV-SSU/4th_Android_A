package com.example.groomton_android_a_base.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

import com.example.groomton_android_a_base.ui.component.SearchBar
import com.example.groomton_android_a_base.model.ExploreFeed
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Scaffold // Preview에서 임시 Scaffold 사용을 위해 다시 import

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    feeds: List<ExploreFeed>,
    innerPadding: PaddingValues,
    //  query: String, onQueryChange: (String) -> Unit 파라미터 제거
    modifier: Modifier = Modifier
) {
    //  query 상태는 ExploreScreen 내부에서 관리
    var query by rememberSaveable { mutableStateOf("") } //  이 줄은 유지
    val filteredFeeds = if (query.isBlank()) feeds else feeds.filter {
        it.user.name.contains(query, ignoreCase = true)
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(3),
        modifier = modifier
            .padding(innerPadding)
            .fillMaxSize(),
        verticalItemSpacing = 1.dp,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
        }
        items(filteredFeeds) { feed ->
            val aspect = when (feed.size) {
                1 -> 1f
                2 -> 0.5f
                else -> 1f
            }

            GlideImage(
                model = feed.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(aspect)
                    .background(Color.Gray),
                contentScale = ContentScale.Crop
            )
        }
    }

}


@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun ExploreScreenPreview() {
    androidx.compose.material3.Scaffold { innerPadding ->
        ExploreScreen(
            feeds = SampleDataProvider.sampleExploreFeeds,
            innerPadding = innerPadding,
            //  Preview에서도 query, onQueryChange 파라미터 제거
            modifier = Modifier
        )
    }
}