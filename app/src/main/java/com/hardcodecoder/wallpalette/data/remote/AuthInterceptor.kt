package com.hardcodecoder.wallpalette.data.remote

import com.hardcodecoder.wallpalette.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val authorizedRequest = chain.request()
            .newBuilder()
            .addHeader("Authorization", "Client-ID ${BuildConfig.API_KEY}")
            .build()
        return chain.proceed(authorizedRequest)
    }
}