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
            val youTubeService = ServiceList.YouTube
            val searchExtractor = youTubeService.getSearchExtractor(query)
            searchExtractor.fetchPage()
            val searchResults = searchExtractor.initialPage.items
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
            videosLiveData.postValue(resultList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}