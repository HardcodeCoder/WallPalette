package com.hardcodecoder.wallpalette.data

import com.hardcodecoder.wallpalette.data.remote.AuthInterceptor
import com.hardcodecoder.wallpalette.data.remote.UnsplashPhotoService
import com.hardcodecoder.wallpalette.data.repo.UnsplashPhotoRepository
import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import okhttp3.OkHttpClient
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://api.unsplash.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    single {
        retrofit.create(UnsplashPhotoService::class.java)
    }

    single {
        UnsplashPhotoRepository(get())
    }.bind<PhotoRepository>()
}