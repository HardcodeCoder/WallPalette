package com.hardcodecoder.wallpalette.domain.usecase

import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import javax.inject.Inject

class GetLatestPhotos @Inject constructor(
    private val repo: PhotoRepository
) {
    suspend operator fun invoke(page: Int) = repo.getLatestPhotos(page = page)
}