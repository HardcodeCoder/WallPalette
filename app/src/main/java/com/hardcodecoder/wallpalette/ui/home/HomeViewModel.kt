package com.hardcodecoder.wallpalette.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.domain.model.Result
import com.hardcodecoder.wallpalette.domain.usecase.GetLatestPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLatestPhotos: GetLatestPhotos
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
            fetchPageAsync(page = page)
        }
    }

    private suspend fun fetchPageAsync(page: Int) {
        val photosResult = getLatestPhotos(
            page = page
        )
        when (photosResult) {
            is Result.Success -> {
                val allPhotos = mutableListOf<Photo>().apply {
                    addAll(_photos.value)
                    addAll(photosResult.data)
                }
                _photos.emit(allPhotos)
            }

            is Result.Error -> {
                // Show error state in ui
            }
        }
    }
}