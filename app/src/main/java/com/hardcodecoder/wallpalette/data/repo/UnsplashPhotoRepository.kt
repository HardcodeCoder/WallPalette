package com.hardcodecoder.wallpalette.data.repo

import com.hardcodecoder.wallpalette.data.remote.UnsplashPhotoService
import com.hardcodecoder.wallpalette.data.toPhoto
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.domain.model.Result
import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class UnsplashPhotoRepository(
    private val unsplashPhotoService: UnsplashPhotoService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : PhotoRepository {

    override suspend fun getLatestPhotos(
        photosPerPage: Int,
        page: Int
    ): Result<List<Photo>> = withContext(dispatcher) {
        try {
            val data = unsplashPhotoService.getLatestUnsplashPhotos(
                photosPerPage = photosPerPage,
                page = page
            ).map { it.toPhoto() }
            Result.Success(data)
        } catch (httpException: HttpException) {
            Result.Error(httpException.code(), httpException.cause)
        } catch (e: Exception) {
            Result.Error(-1, e.cause)
        }
    }

    override suspend fun searchPhotos(
        query: String,
        resultPerPage: Int,
        page: Int
    ): Result<List<Photo>> = withContext(dispatcher) {
        try {
            val data = unsplashPhotoService.searchUnsplashPhotos(
                query = query,
                resultPerPage = resultPerPage,
                page = page
            ).results.map { it.toPhoto() }
            Result.Success(data)
        } catch (httpException: HttpException) {
            Result.Error(httpException.code(), httpException.cause)
        } catch (e: Exception) {
            Result.Error(-1, e.cause)
        }
    }
}