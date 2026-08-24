package com.span.videoplayer.utils

import java.util.Locale

fun formatFileSize(bytes: Long): String {
    val kb = 1024.0
    return when {
        bytes >= kb * kb -> String.format(Locale.getDefault(), "%.1f MB", bytes / (kb * kb))
        bytes >= kb -> String.format(Locale.getDefault(), "%.0f KB", bytes / kb)
        else -> "$bytes B"
    }
}

fun formatDuration(durationMs: Long): String {
    val totalSeconds = durationMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
}
