package com.hardcodecoder.wallpalette.data.model

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("description") val description: String?,
    @SerializedName("alt_description") val altDescription: String?,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("blur_hash") val blurHash: String,
    @SerializedName("color") val color: String,
    @SerializedName("urls") val url: UnsplashPhotoUrl,
    @SerializedName("likes") val likes: Int
)