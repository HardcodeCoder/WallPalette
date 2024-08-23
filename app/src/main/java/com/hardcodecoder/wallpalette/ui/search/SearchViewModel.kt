package com.hardcodecoder.wallpalette.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.domain.model.Result
import com.hardcodecoder.wallpalette.domain.usecase.SearchPhotos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchForPhotos: SearchPhotos
) : ViewModel() {

    private val _searchResult = MutableStateFlow(emptyList<Photo>())
    private var searchJob: Job? = null
    private var currentPage = 1
    private var currentQuery = ""
    val searchResult = _searchResult.asStateFlow()

    fun searchPhotos(query: String) {
        currentQuery = query
        currentPage = 1
        _searchResult.value = emptyList()

        if (currentQuery.isBlank()) return
        searchPhotos(currentQuery, currentPage)
    }

    fun updateScrollPosition(position: Int) {
        if (position >= _searchResult.value.size - 5 && searchJob?.isActive == false) {
            searchPhotos(currentQuery, ++currentPage)
        }
    }

    private fun searchPhotos(query: String, page: Int) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch { searchPhotosAsync(query, page) }
    }

    private suspend fun searchPhotosAsync(query: String, page: Int) {
        val photosResult = searchForPhotos(
            query = query,
            page = page
        )
        when (photosResult) {
            is Result.Success -> {
                val allPhotos = mutableListOf<Photo>().apply {
                    addAll(_searchResult.value)
                    addAll(photosResult.data)
                }
                _searchResult.emit(allPhotos)
            }

            is Result.Error -> {
                // Show error state in ui
            }
        }
    }
}