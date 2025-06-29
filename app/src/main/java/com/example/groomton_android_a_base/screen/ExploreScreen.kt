package com.example.groomton_android_a_base.screen


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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.groomton_android_a_base.model.ExploreFeed
import com.example.groomton_android_a_base.sampledata.SampleDataProvider

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ExploreScreen(feeds : List<ExploreFeed>){

    Scaffold {innerPadding ->
        LazyVerticalStaggeredGrid (
            columns = StaggeredGridCells.Fixed(3),
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalItemSpacing = 1.dp,
            horizontalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            item(span = StaggeredGridItemSpan.FullLine){
                SearchBar()
            }
            items(feeds) {feed ->
                val imageModifier = when(feed.size){
                    1 -> Modifier.fillMaxWidth().aspectRatio(1f)
                    2 -> Modifier.fillMaxWidth().aspectRatio(0.5f)
                    else -> Modifier.fillMaxWidth().aspectRatio(1f)
                }

                GlideImage(
                    model = feed.imageUrl,
                    contentDescription = null,
                    modifier = imageModifier.fillMaxWidth().background(Color.Gray),
                    contentScale = ContentScale.Crop
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