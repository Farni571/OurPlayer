package com.example.ourplayer

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.schabi.newpipe.extractor.Collector
import org.schabi.newpipe.extractor.InfoItem
import org.schabi.newpipe.extractor.ListExtractor
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.services.youtube.linkHandler.YoutubeStreamLinkHandlerFactory
import org.schabi.newpipe.extractor.stream.StreamExtractor
import org.schabi.newpipe.extractor.NewPipe
import org.schabi.newpipe.extractor.downloader.Downloader

class MediaExtractor {


    suspend fun extractService() = withContext(Dispatchers.IO) {
        val page: ListExtractor.InfoItemsPage<InfoItem>
        val kioskId = "Trending"
        val youTubeService = ServiceList.YouTube

        val kioskExtractor1 = youTubeService.kioskList.getExtractorById("Trending", null)

        val kioskExtractor = youTubeService.kioskList.getExtractorById("Trending", null)

        val searchExtractor = youTubeService.getSearchExtractor("mae")
        searchExtractor.apply {
            fetchPage()
            page = initialPage
        }

        val x = page.items.get(0)

    }
}