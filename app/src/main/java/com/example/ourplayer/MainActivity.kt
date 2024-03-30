package com.example.ourplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberImagePainter
import com.example.ourplayer.data.VideoItem
import com.example.ourplayer.presentation.ViewModel
import com.example.ourplayer.ui.theme.OurPlayerTheme
import com.facebook.flipper.BuildConfig
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import kotlinx.coroutines.launch
import org.schabi.newpipe.extractor.NewPipe

class MainActivity : ComponentActivity() {
    val viewModel = ViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SoLoader.init(this, false)

        if (FlipperUtils.shouldEnableFlipper(this)) {
            val client = AndroidFlipperClient.getInstance(this)

            client.addPlugin(NetworkFlipperPlugin())
            client.start()
        }

        setContent {
            OurPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }

        NewPipe.init(DownloaderImpl.init(null))

        lifecycleScope.launch {

            viewModel.searchYouTubeVideos("vfr")

        }



    }

    @Composable
    fun VideosList(videos: List<VideoItem>) {
        LazyColumn {
            items(videos) { video ->
                VideoItemView(video)
            }
        }
    }

    @Composable
    fun VideoItemView(video: VideoItem) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = video.title, style = MaterialTheme.typography.displayMedium)
            // Отображение миниатюры, если вы хотите добавить Image
            // Помните, что для загрузки изображений в Compose вам потребуется использовать библиотеку, например Coil
            Image(
                painter = rememberImagePainter(video.thumbnailUrl),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
            )
        }
    }

    companion object {
        const val DEBUG = !BuildConfig.BUILD_TYPE.equals("release")
    }
}

@Composable
fun VideosScreen(viewModel: ViewModel = ViewModel()) {
    viewModel.videosLiveData.ob
    val videos by viewModel.videosLiveData.observe(emptyList())
    VideosList(videos)
}

@Composable
fun VideosList(videos: List<VideoItem>) {
    LazyColumn {
        items(videos) { video ->
            VideoItemView(video)
        }
    }
}

@Composable
fun VideoItemView(video: VideoItem) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = video.title, style = MaterialTheme.typography.displayMedium)
        Image(
            painter = rememberImagePainter(video.thumbnailUrl),
            contentDescription = null,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OurPlayerTheme {
        Greeting("Android")
    }
}