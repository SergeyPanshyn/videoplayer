package com.span.videoplayer.presentation.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.PlayerView
import com.span.videoplayer.ui.theme.BackIcon
import com.span.videoplayer.ui.theme.PauseIcon
import com.span.videoplayer.ui.theme.PlayIcon
import com.span.videoplayer.ui.theme.SkipIcon
import com.span.videoplayer.utils.formatDuration

@Composable
fun VideoPlayerScreen(
    onBack: () -> Unit, viewModel: VideoPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var controlsVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) { controlsVisible = !controlsVisible }) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = viewModel.player
                    useController = false
                }
            }, modifier = Modifier.fillMaxSize()
        )

        AnimatedVisibility(
            visible = controlsVisible,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                BackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp), onBack = onBack
                )

                CenterControls(
                    modifier = Modifier.align(Alignment.Center),
                    isPlaying = state.isPlaying,
                    onSeekBack = viewModel::seekBack,
                    onSeekForward = viewModel::seekForward,
                    onPlayPauseToggle = viewModel::togglePlayPause
                )

                BottomControls(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(16.dp),
                    progress = state.progress,
                    durationMs = state.durationMs,
                    currentPositionMs = state.currentPositionMs,
                    onSeekStart = viewModel::onSeekStart,
                    onSeekEnd = viewModel::onSeekEnd
                )
            }
        }
    }
}

@Composable
fun BackButton(
    modifier: Modifier, onBack: () -> Unit
) {
    IconButton(
        modifier = modifier, onClick = { onBack() }) {
        Icon(
            imageVector = BackIcon, contentDescription = "Back", tint = Color.White
        )
    }
}

@Composable
fun CenterControls(
    modifier: Modifier,
    isPlaying: Boolean,
    onSeekBack: () -> Unit,
    onSeekForward: () -> Unit,
    onPlayPauseToggle: () -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onSeekBack() }) {
            Icon(
                imageVector = SkipIcon, contentDescription = "Skip back 10s", tint = Color.White
            )
        }

        IconButton(
            onClick = {
                onPlayPauseToggle()
            },
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            val imageVector = if (isPlaying) PauseIcon else PlayIcon
            Icon(
                imageVector = imageVector, contentDescription = "Play/Pause", tint = Color.White
            )
        }

        IconButton(
            onClick = { onSeekForward() }, modifier = Modifier.scale(-1f, 1f)
        ) {
            Icon(
                imageVector = SkipIcon, contentDescription = "Skip froward 10s", tint = Color.White
            )
        }
    }
}

@Composable
fun BottomControls(
    modifier: Modifier,
    progress: Float,
    durationMs: Long,
    currentPositionMs: Long,
    onSeekStart: () -> Unit,
    onSeekEnd: (Long) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        var isSeeking by remember { mutableStateOf(false) }
        var seekProgress by remember { mutableStateOf(0f) }

        val displayedProgress = if (isSeeking) seekProgress else progress

        Slider(
            value = displayedProgress, onValueChange = { fraction ->
            if (!isSeeking) onSeekStart()
            isSeeking = true
            seekProgress = fraction
        }, onValueChangeFinished = {
            onSeekEnd((seekProgress * durationMs).toLong())
            isSeeking = false
        }, colors = SliderDefaults.colors(
            thumbColor = Color.White, activeTrackColor = Color.White
        )
        )
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // TODO: Format duration should be moved to business logic
            Text(text = formatDuration(currentPositionMs), color = Color.White)
            Text(text = formatDuration(durationMs), color = Color.White)
        }
    }
}
