package com.hardcodecoder.wallpalette.domain.model

data class Photo(
    val id: String,
    val description: String,
    val width: Int,
    val height: Int,
    val aspectRatio: Float,
    val blurHash: String,
    val color: Int,
    val thumbnailUrl: String,
    val imageUrl: String,
    val likes: Int
)