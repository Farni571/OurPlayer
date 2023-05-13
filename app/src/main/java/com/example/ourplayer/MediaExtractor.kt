package com.example.ourplayer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory
import org.schabi.newpipe.extractor.stream.StreamExtractor
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.downloader.Downloader

class MediaExtractor {


    suspend fun extractService() = withContext(Dispatchers.IO) {

        val kioskId = "Trending"
        val youTubeService = ServiceList.YouTube

        val kioskExtractor = youTubeService.getKioskList().getExtractorById("Trending", null)
        val page = kioskExtractor.fetchPage()
            //  kioskExtractor.fetchPage()
    }
}