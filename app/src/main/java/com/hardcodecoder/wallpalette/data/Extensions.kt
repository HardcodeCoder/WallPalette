package com.hardcodecoder.wallpalette.data

import android.graphics.Color
import com.hardcodecoder.wallpalette.data.model.UnsplashPhoto
import com.hardcodecoder.wallpalette.domain.model.Photo

fun UnsplashPhoto.toPhoto() = Photo(
    id = id,
    description = description ?: "",
    aspectRatio = width.toFloat() / if (height > 0) height else width,
    blurHash = blurHash,
    color = Color.parseColor(color),
    thumbnailUrl = url.thumbUrl,
    imageUrl = url.fullUrl
)