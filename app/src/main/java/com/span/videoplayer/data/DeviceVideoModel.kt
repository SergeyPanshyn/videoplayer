package com.span.videoplayer.data

import android.net.Uri

data class DeviceVideoModel(
    val id: Long,
    val uri: Uri,
    val title: String,
    val durationMs: Long,
    val sizeBytes: Long,
    val dateAdded: Long
)