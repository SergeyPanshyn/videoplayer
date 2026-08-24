package com.span.videoplayer.presentation.list

import android.net.Uri
import com.span.videoplayer.data.DeviceVideoModel

sealed class VideosListUiState {
    object Loading: VideosListUiState()
    data class Error(val message: String): VideosListUiState()
    data class Content(val videos: List<VideoUiModel>): VideosListUiState()
}

data class VideoUiModel(
    val id: Long,
    val title: String,
    val fileSize: String,
    val duration: String,
    val thumbnailUrl: String,
    val videoUri: Uri
)

// TODO Finish mapping logic
fun DeviceVideoModel.toUiModel() = VideoUiModel(
    id = id,
    title = title,
    fileSize = sizeBytes.toString(),
    thumbnailUrl = "",
    duration = durationMs.toString(),
    videoUri = uri
)