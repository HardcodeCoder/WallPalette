package com.hardcodecoder.wallpalette.data

import com.hardcodecoder.wallpalette.data.remote.AuthInterceptor
import com.hardcodecoder.wallpalette.data.remote.UnsplashPhotoService
import com.hardcodecoder.wallpalette.data.repo.UnsplashPhotoRepository
import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun getOkhttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .client(getOkhttpClient())
        .baseUrl("https://api.unsplash.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getUnsplashService(): UnsplashPhotoService =
        getRetrofit().create(UnsplashPhotoService::class.java)

    @Provides
    @Singleton
    fun getPhotoRepo(): PhotoRepository = UnsplashPhotoRepository(getUnsplashService())
}