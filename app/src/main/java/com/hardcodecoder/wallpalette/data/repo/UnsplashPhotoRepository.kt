package com.hardcodecoder.wallpalette.data.repo

import com.hardcodecoder.wallpalette.data.remote.UnsplashPhotoService
import com.hardcodecoder.wallpalette.data.toPhoto
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UnsplashPhotoRepository(
    private val unsplashPhotoService: UnsplashPhotoService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PhotoRepository {

    override suspend fun getLatestPhotos(
        photosPerPage: Int,
        page: Int
    ): List<Photo> = withContext(dispatcher) {
        unsplashPhotoService.getLatestUnsplashPhotos(
            photosPerPage = photosPerPage,
            page = page
        ).map { it.toPhoto() }
    }

    override suspend fun searchPhotos(
        query: String,
        resultPerPage: Int,
        page: Int
    ): List<Photo> = withContext(dispatcher) {
        unsplashPhotoService.searchUnsplashPhotos(
            query = query,
            resultPerPage = resultPerPage,
            page = page
        ).map { it.toPhoto() }
    }
}