package com.span.videoplayer.presentation.list

import android.net.Uri
import com.span.videoplayer.data.VideoDataModel
import com.span.videoplayer.utils.formatDuration
import com.span.videoplayer.utils.formatFileSize

sealed class VideoListUiState {
    object Loading : VideoListUiState()
    object Empty : VideoListUiState()
    data class Error(val message: String) : VideoListUiState()
    data class Content(val list: List<VideoUiModel>) : VideoListUiState()
}

data class VideoUiModel(
    val id: Long, val title: String, val description: String, val uri: Uri
)

fun VideoDataModel.toUiModel() = VideoUiModel(
    id = id, title = title, description = StringBuilder().apply {
        append(formatDuration(durationMs))
        append(' ')
        append('·')
        append(' ')
        append(formatFileSize(sizeBytes))
    }.toString(), uri = uri
)
