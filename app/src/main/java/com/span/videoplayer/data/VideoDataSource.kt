package com.span.videoplayer.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class VideoDataSource @Inject constructor(
    @ApplicationContext val context: Context
) {

    enum class SortOrder {
        DATE_ADDED, DURATION
    }

    fun getVideoList(sortOrder: SortOrder): List<VideoDataModel> {
        val collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_ADDED
        )

        val sortOrder = when (sortOrder) {
            SortOrder.DATE_ADDED -> MediaStore.Video.Media.DATE_ADDED
            SortOrder.DURATION -> MediaStore.Video.Media.DURATION
        }

        val videoList = mutableListOf<VideoDataModel>()

        context.contentResolver.query(
            collection, projection, null, null, sortOrder
        )?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val dateCol = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val uri = ContentUris.withAppendedId(collection, id)

                videoList += VideoDataModel(
                    id = id,
                    title = cursor.getString(nameCol) ?: "Untitled",
                    uri = uri,
                    durationMs = cursor.getLong(durationCol),
                    sizeBytes = cursor.getLong(sizeCol),
                    dateAdded = cursor.getLong(dateCol)
                )
            }

        }
        return videoList
    }
}
