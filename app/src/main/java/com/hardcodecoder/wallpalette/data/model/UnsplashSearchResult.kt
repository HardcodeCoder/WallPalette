package com.hardcodecoder.wallpalette.data.model

import com.google.gson.annotations.SerializedName

data class UnsplashSearchResult(
    @SerializedName("results") val results: List<UnsplashPhoto>
)
