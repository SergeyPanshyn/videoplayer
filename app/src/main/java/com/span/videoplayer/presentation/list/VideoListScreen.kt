package com.span.videoplayer.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.span.videoplayer.ui.theme.PurpleGrey40

@Composable
fun VideoListScreen(
    viewModel: VideoListViewModel = hiltViewModel(),
    onVideoClick: (Long) -> Unit
) {
    when(val state = viewModel.state.collectAsState().value) {
        VideosListUiState.Loading -> CircularProgressIndicator()
        is VideosListUiState.Error -> Text(state.message)
        is VideosListUiState.Content -> VideoList(state.videos) {
            onVideoClick(it)
        }
    }
}

@Composable
fun VideoList(list: List<VideoUiModel>, onItemClick: (Long) -> Unit) {
    LazyColumn {
        items(list, { it.id}) { item ->
            VideoItem(item, onItemClick)
        }
    }
}

@Composable
fun VideoItem(video: VideoUiModel, onItemClick: (Long) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(video.id) })
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(4.dp)
                .background(PurpleGrey40))
        {

        }
        Text(video.title)
        Text(text = video.fileSize)
        Text(video.duration)
    }
}