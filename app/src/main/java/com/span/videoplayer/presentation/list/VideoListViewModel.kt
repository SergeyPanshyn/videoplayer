package com.span.videoplayer.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.span.videoplayer.data.VideoSortOrder
import com.span.videoplayer.data.VideosDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val videosDataSource: VideosDataSource
): ViewModel() {

    private val _state = MutableStateFlow<VideosListUiState>(VideosListUiState.Loading)
    val state: StateFlow<VideosListUiState> = _state.asStateFlow()

    init {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelScope.launch {
            _state.value = VideosListUiState.Loading
            runCatching {
                videosDataSource.getVideos(VideoSortOrder.DATE)
            }.onSuccess { videos ->
                if (videos.isEmpty()) {
                    _state.value = VideosListUiState.Error("No videos available.")
                } else {
                    _state.value = VideosListUiState.Content(videos.map { it.toUiModel() })
                }
            }.onFailure {
                _state.value = VideosListUiState.Error("Failed to load a videos.")
            }
        }
    }
}