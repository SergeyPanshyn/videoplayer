package com.span.videoplayer.presentation.player

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val videoId: Long = checkNotNull(savedStateHandle["videoId"])

    private val _state = MutableStateFlow(VideoPlayerUiState())
    val state = _state.asStateFlow()

    private var progressJob: Job? = null

    val player = ExoPlayer.Builder(context).build().apply {
        val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId)
        setMediaItem(MediaItem.fromUri(uri))
        addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _state.update { it.copy(isPlaying = isPlaying) }
                if (isPlaying) startProgressUpdates() else progressJob?.cancel()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY) {
                    _state.update { it.copy(durationMs = duration.coerceAtLeast(0L)) }
                }
            }
        })
        prepare()
        playWhenReady = true
    }

    private fun startProgressUpdates() {
        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            while (isActive) {
                _state.update { it.copy(currentPositionMs = player.currentPosition) }
                delay(500.milliseconds)
            }
        }
    }

    fun togglePlayPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            if (player.playbackState == Player.STATE_ENDED) {
                player.seekTo(0)
            }
            player.play()
        }
    }

    private var wasPlayingBeforeSeek = false

    fun onSeekStart() {
        wasPlayingBeforeSeek = player.isPlaying
        if (player.isPlaying) player.pause()
    }

    fun onSeekEnd(positionMs: Long) {
        player.seekTo(positionMs)
        _state.update { it.copy(currentPositionMs = positionMs) }
        if (wasPlayingBeforeSeek) player.play()
    }

    fun seekBack() {
        player.seekBack()
    }

    fun seekForward() {
        player.seekForward()
    }

    override fun onCleared() {
        progressJob?.cancel()
        player.stop()
        player.release()
        super.onCleared()
    }

}