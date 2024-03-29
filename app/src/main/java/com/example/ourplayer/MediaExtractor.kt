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
import org.schabi.newpipe.extractor.stream.StreamInfoItem

class MediaExtractor {


    suspend fun searchYouTubeVideos(query: String) = withContext(Dispatchers.IO) {
        try {
            // Получаем экземпляр сервиса YouTube
            val youTubeService = ServiceList.YouTube

            // Создаем экстрактор для поиска
            val searchExtractor = youTubeService.getSearchExtractor(query)

            // Выполняем запрос
            searchExtractor.fetchPage()

            // Получаем начальную страницу с результатами поиска
            val searchResults = searchExtractor.initialPage.items

            // Обработка результатов поиска
            if (searchResults.isNotEmpty()) {
                searchResults.forEach { video ->
                    if (video is StreamInfoItem) {
                        // Вывод информации о каждом видео
                        println("Video title: ${video.name}, URL: ${video.url}")
                    }
                }
            } else {
                println("No videos found for query: $query")
            }
        } catch (e: Exception) {
            // Обработка исключений (например, логирование ошибки)
            e.printStackTrace()
        }
    }
}