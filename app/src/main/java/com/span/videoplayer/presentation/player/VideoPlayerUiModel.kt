package com.span.videoplayer.presentation.player

data class VideoPlayerUiState(
    val isPlaying: Boolean = false,
    val currentPositionMs: Long = 0L,
    val durationMs: Long = 0L
) {
    val progress: Float
        get() = if (durationMs > 0) currentPositionMs.toFloat() / durationMs.toFloat() else 0f
}
