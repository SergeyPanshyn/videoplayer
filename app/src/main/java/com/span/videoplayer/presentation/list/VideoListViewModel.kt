package com.span.videoplayer.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.span.videoplayer.data.VideoDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoListViewModel @Inject constructor(
    private val videoDataSource: VideoDataSource
) : ViewModel() {

    private val _state = MutableStateFlow<VideoListUiState>(VideoListUiState.Loading)
    val state: StateFlow<VideoListUiState> = _state.asStateFlow()

    private var sortOption = SortOption.DATE_ADDED

    init {
        loadVideo()
    }

    private fun loadVideo() {
        viewModelScope.launch {
            _state.update { VideoListUiState.Loading }
            runCatching {
                videoDataSource.getVideoList(sortOption.toDataSort())
            }.onSuccess { list ->
                if (list.isEmpty()) {
                    _state.update { VideoListUiState.Error("No video available.") }
                } else {
                    _state.update { VideoListUiState.Content(list.map { it.toUiModel() }) }
                }
            }.onFailure {
                _state.update { VideoListUiState.Error("Failed to load a video.") }
            }
        }
    }

    fun onSortChanged(sortOption: SortOption) {
        this@VideoListViewModel.sortOption = sortOption
        loadVideo()
    }
}
