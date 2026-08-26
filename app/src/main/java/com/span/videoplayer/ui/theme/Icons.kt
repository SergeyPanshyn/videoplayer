package com.span.videoplayer.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val SortIcon: ImageVector
    get() {
        return ImageVector.Builder(
            name = "Sort",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(3f, 18f)
                lineTo(11f, 18f)
                lineTo(11f, 16f)
                lineTo(3f, 16f)
                close()
                moveTo(3f, 6f)
                verticalLineTo(8f)
                horizontalLineTo(21f)
                verticalLineTo(6f)
                close()
                moveTo(3f, 13f)
                horizontalLineTo(17f)
                verticalLineTo(11f)
                horizontalLineTo(3f)
                close()
            }
        }.build()
    }

val BackIcon: ImageVector
    get() {
        return ImageVector.Builder(
            name = "Back",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(20f, 11f)
                horizontalLineTo(7.83f)
                lineTo(13.42f, 5.41f)
                lineTo(12f, 4f)
                lineTo(4f, 12f)
                lineTo(12f, 20f)
                lineTo(13.41f, 18.59f)
                lineTo(7.83f, 13f)
                horizontalLineTo(20f)
                verticalLineTo(11f)
                close()
            }
        }.build()
    }

val SkipIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Skip", defaultWidth = 24.dp, defaultHeight = 24.dp,
        viewportWidth = 24f, viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(12f, 5f)
            verticalLineTo(1f)
            lineTo(7f, 6f)
            lineTo(12f, 11f)
            verticalLineTo(7f)
            curveTo(15.31f, 7f, 18f, 9.69f, 18f, 13f)
            curveTo(18f, 16.31f, 15.31f, 19f, 12f, 19f)
            curveTo(8.69f, 19f, 6f, 16.31f, 6f, 13f)
            horizontalLineTo(4f)
            curveTo(4f, 17.42f, 7.58f, 21f, 12f, 21f)
            curveTo(16.42f, 21f, 20f, 17.42f, 20f, 13f)
            curveTo(20f, 8.58f, 16.42f, 5f, 12f, 5f)
            close()
        }
    }.build()

val PlayIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Play", defaultWidth = 24.dp, defaultHeight = 24.dp,
        viewportWidth = 24f, viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(8f, 5f)
            verticalLineTo(19f)
            lineTo(19f, 12f)
            close()
        }
    }.build()

val PauseIcon: ImageVector
    get() = ImageVector.Builder(
        name = "Pause", defaultWidth = 24.dp, defaultHeight = 24.dp,
        viewportWidth = 24f, viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(6f, 19f)
            horizontalLineTo(10f)
            verticalLineTo(5f)
            horizontalLineTo(6f)
            close()
            moveTo(14f, 5f)
            verticalLineTo(19f)
            horizontalLineTo(18f)
            verticalLineTo(5f)
            close()
        }
    }.build()