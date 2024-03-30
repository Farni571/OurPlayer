package com.example.ourplayer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.ourplayer.data.VideoItem


@Composable
fun VideosScreen(viewModel: ViewModel) {
    val videos by viewModel.videosLiveData.observeAsState(emptyList())

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "profile") {
        composable("initial") {
            InitialVideosList(
                videos,
                onNavigateToSettings = { navController.navigate("settings") })
        }
        composable("settings") { }
    }
}

@Composable
fun InitialVideosList(
    videos: List<VideoItem>,
    onNavigateToSettings: () -> Unit
) {
    LazyColumn {
        items(videos) { video ->
            VideoItemView(video)
        }

    }
    Button(onClick =  onNavigateToSettings ) {
        Text("Go to Settings")
    }
}

@Composable
fun VideoItemView(video: VideoItem) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = video.title, style = MaterialTheme.typography.displayMedium)
        Image(
            painter = rememberAsyncImagePainter(video.thumbnailUrl),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
        )
    }
}