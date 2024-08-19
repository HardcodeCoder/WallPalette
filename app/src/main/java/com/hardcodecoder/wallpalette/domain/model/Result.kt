package com.hardcodecoder.wallpalette.domain.model

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val code: Int, val throwable: Throwable? = null) : Result<T>()
}