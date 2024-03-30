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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.ourplayer.data.VideoItem
import com.example.ourplayer.presentation.InitialVideosList
import com.example.ourplayer.presentation.VideosScreen
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
                    VideosScreen(viewModel)
                }
            }
        }

        NewPipe.init(DownloaderImpl.init(null))

        lifecycleScope.launch {
            viewModel.searchYouTubeVideos("vfr")
        }
    }

    companion object {
        const val DEBUG = !BuildConfig.BUILD_TYPE.equals("release")
    }
}






@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OurPlayerTheme {

    }
}