package com.hardcodecoder.wallpalette.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.domain.repo.PhotoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos = MutableStateFlow(emptyList<Photo>())
    private val buffer = 5
    private var fetchJob: Job? = null
    private var currentPage = 1
    val photos = _photos.asStateFlow()

    init {
        fetchPage(currentPage)
    }

    fun updateScrollPosition(position: Int) {
        if (position > _photos.value.size - buffer && fetchJob?.isActive == false) {
            fetchPage(++currentPage)
        }
    }

    private fun fetchPage(page: Int) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val newPhotos = photoRepository.getLatestPhotos(
                page = page
            )
            val allPhotos = mutableListOf<Photo>().apply {
                addAll(_photos.value)
                addAll(newPhotos)
            }
            _photos.emit(allPhotos)
        }
    }
}