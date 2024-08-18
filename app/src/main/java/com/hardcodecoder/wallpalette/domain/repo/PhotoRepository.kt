package com.hardcodecoder.wallpalette.domain.repo

import com.hardcodecoder.wallpalette.domain.model.Photo

interface PhotoRepository {

    suspend fun getLatestPhotos(
        photosPerPage: Int = 50,
        page: Int = 1
    ): List<Photo>

    suspend fun searchPhotos(
        query: String,
        resultPerPage: Int = 50,
        page: Int = 1
    ): List<Photo>
}