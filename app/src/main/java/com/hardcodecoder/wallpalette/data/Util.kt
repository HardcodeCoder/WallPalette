package com.hardcodecoder.wallpalette.data

import android.graphics.Color
import com.hardcodecoder.wallpalette.data.model.UnsplashPhoto
import com.hardcodecoder.wallpalette.domain.model.Photo

fun UnsplashPhoto.toPhoto() = Photo(
    id = id,
    description = description ?: "",
    aspectRatio = getRoundedAspectRatio(width, height),
    blurHash = blurHash,
    color = Color.parseColor(color),
    thumbnailUrl = url.thumbUrl,
    imageUrl = url.fullUrl
)

// Returns aspect ratio rounded to one decimal place.
private fun getRoundedAspectRatio(width: Int, height: Int): Float {
    val h = if (height > 0) height else width
    return (width.toFloat() / h.toFloat() * 10).toInt() / 10f
}