package com.span.videoplayer.presentation.list

import com.span.videoplayer.data.VideoDataSource

enum class SortOption { DATE_ADDED, DURATION }

fun SortOption.toDataSort(): VideoDataSource.SortOrder = when (this) {
    SortOption.DATE_ADDED -> VideoDataSource.SortOrder.DATE_ADDED
    SortOption.DURATION -> VideoDataSource.SortOrder.DURATION
}
