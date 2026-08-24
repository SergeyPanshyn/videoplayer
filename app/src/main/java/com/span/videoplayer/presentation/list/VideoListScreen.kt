package com.span.videoplayer.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.span.videoplayer.ui.theme.SortIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoListScreen(
    viewModel: VideoListViewModel = hiltViewModel(), onVideoClick: (Long) -> Unit
) {
    var sortMenuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Video") }, actions = {
                IconButton(onClick = { sortMenuExpanded = true }) {
                    Icon(SortIcon, contentDescription = "Sort")
                }
                DropdownMenu(
                    expanded = sortMenuExpanded, onDismissRequest = { sortMenuExpanded = false }) {
                    DropdownMenuItem(text = { Text("Date added") }, onClick = {
                        viewModel.onSortChanged(SortOption.DATE_ADDED)
                        sortMenuExpanded = false
                    })
                    DropdownMenuItem(text = { Text("Duration") }, onClick = {
                        viewModel.onSortChanged(SortOption.DURATION)
                        sortMenuExpanded = false
                    })
                }
            })
        }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val state = viewModel.state.collectAsState().value) {
                VideoListUiState.Loading -> CircularProgressIndicator()
                VideoListUiState.Empty -> Text("No videos available.")
                is VideoListUiState.Error -> Text(state.message)
                is VideoListUiState.Content -> VideoList(
                    list = state.list, onItemClick = { onVideoClick(it) })
            }
        }
    }
}

@Composable
fun VideoList(
    list: List<VideoUiModel>, onItemClick: (Long) -> Unit
) {
    LazyColumn {
        items(list, { it.id }) { item ->
            VideoItem(item, onItemClick)
            HorizontalDivider(
                color = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

@Composable
fun VideoItem(
    video: VideoUiModel, onItemClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(video.id) })
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        AsyncImage(
            model = video.uri,
            contentDescription = video.title,
            contentScale = ContentScale.Crop,
            placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            error = ColorPainter(MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier
                .size(96.dp, 64.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = video.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1
            )
            Text(
                text = video.description,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )

        }
    }
}
