package com.hardcodecoder.wallpalette.domain.usecase

import com.hardcodecoder.wallpalette.domain.model.Result
import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import javax.inject.Inject

class SearchPhotos @Inject constructor(
    private val repo: PhotoRepository
) {
    suspend operator fun invoke(
        query: String,
        page: Int
    ) = if (query.isBlank()) Result.Success(emptyList()) else repo.searchPhotos(
        query = query,
        page = page
    )
}