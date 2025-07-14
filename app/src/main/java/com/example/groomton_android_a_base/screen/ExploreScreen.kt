package com.example.groomton_android_a_base.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.groomton_android_a_base.model.ExploreFeed
import com.example.groomton_android_a_base.sampledata.SampleDataProvider
import com.example.groomton_android_a_base.ui.component.ExploreScreen.SearchBar
import com.example.groomton_android_a_base.viewmodel.FeedViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExploreScreen(navController: NavController, feeds : List<ExploreFeed>){
    var query by rememberSaveable { mutableStateOf("") }

    var filteredExploreFeeds = if (query.isEmpty()) feeds else feeds.filter {
        it.user.name.contains(query, ignoreCase = true)
    }
    Scaffold {innerPadding ->
        LazyVerticalStaggeredGrid (
            columns = StaggeredGridCells.Fixed(3),
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalItemSpacing = 1.dp,
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            item(span = StaggeredGridItemSpan.FullLine){
                SearchBar(query = query, onQueryChange = {query = it})
            }
            items(filteredExploreFeeds) {exploreFeed ->
                val aspect = when(exploreFeed.size){
                    1 -> 1f
                    2 -> 0.5f
                    else -> 1f
                }

                GlideImage(
                    model = exploreFeed.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.aspectRatio(aspect).fillMaxWidth().background(Color.Gray)
                        .clickable {
                            navController.navigate("feed_detail/${exploreFeed.feed.id}")
                        },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 730)
@Composable
fun ExploreScreenPreview() {
    val navController = rememberNavController()
    ExploreScreen(navController = navController, feeds = SampleDataProvider.sampleExploreFeeds)
}