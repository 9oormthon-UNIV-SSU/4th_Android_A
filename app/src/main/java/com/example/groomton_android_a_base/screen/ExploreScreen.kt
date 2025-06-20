package com.example.week_06.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.week_06.dataclass.ExploreFeed
import com.example.week_06.sampledata.SampleDataProvider
import com.example.week_06.ui.component.BottomBar

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExploreScreen(feeds : List<ExploreFeed>){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ){innerPadding ->
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Fixed(3),
            modifier = Modifier.padding(innerPadding).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalItemSpacing = 2.dp
        ) {
            item(span = StaggeredGridItemSpan.FullLine){
                SearchBar()
            }
            items(feeds) {feed ->
                val height = when(feed.size){
                    1 -> 100.dp
                    2 -> 200.dp
                    else -> {100.dp}
                }
                GlideImage(
                    model = feed.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.height(height)
                )
            }
        }
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        placeholder = { Text("Search") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}



@Preview(showBackground = true, widthDp = 360, heightDp = 730)
@Composable
fun ExploreScreenPreview() {
    ExploreScreen(feeds = SampleDataProvider.sampleExploreFeeds)
}