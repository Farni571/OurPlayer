package com.example.ourplayer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ourplayer.data.VideoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.schabi.newpipe.extractor.ServiceList
import org.schabi.newpipe.extractor.stream.StreamInfoItem

class ViewModel: ViewModel() {

    val videosLiveData = MutableLiveData<List<VideoItem>>()

    val resultList = mutableListOf<VideoItem>()
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
                        resultList.add(
                            VideoItem(
                                title = video.name,
                                url = video.url,
                                thumbnailUrl = video.thumbnails.get(0).url // Пример получения URL миниатюры
                            )
                        )
                    }
                }
            } else {
                println("No videos found for query: $query")
            }

            videosLiveData.value = resultList
        } catch (e: Exception) {
            // Обработка исключений (например, логирование ошибки)
            e.printStackTrace()
        }
    }



}