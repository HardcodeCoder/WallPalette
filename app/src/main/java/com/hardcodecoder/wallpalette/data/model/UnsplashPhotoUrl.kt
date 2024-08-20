package com.hardcodecoder.wallpalette.data.model

import com.google.gson.annotations.SerializedName

data class UnsplashPhotoUrl(
    @SerializedName("thumb") val thumbUrl: String,
    @SerializedName("regular") val fullUrl: String
)