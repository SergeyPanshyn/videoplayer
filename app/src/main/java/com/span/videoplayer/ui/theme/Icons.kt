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