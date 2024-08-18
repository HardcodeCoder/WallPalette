package com.hardcodecoder.wallpalette.data.remote

import com.hardcodecoder.wallpalette.data.model.UnsplashPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashPhotoService {

    @GET("/photos")
    suspend fun getLatestUnsplashPhotos(
        @Query("per_page") photosPerPage: Int,
        @Query("page") page: Int
    ): List<UnsplashPhoto>

    @GET("/search/photos")
    suspend fun searchUnsplashPhotos(
        @Query("query") query: String,
        @Query("per_page") resultPerPage: Int,
        @Query("page") page: Int
    ): List<UnsplashPhoto>
}