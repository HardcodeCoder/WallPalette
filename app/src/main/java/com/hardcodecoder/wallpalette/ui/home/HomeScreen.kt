package com.hardcodecoder.wallpalette.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hardcodecoder.wallpalette.domain.model.Photo
import com.hardcodecoder.wallpalette.ui.core.PaginatedLazyPhotoGrid
import com.hardcodecoder.wallpalette.ui.core.PhotoDetailsBottomSheet

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val showBottomSheet = remember { mutableStateOf(false) }
    val photo = remember { mutableStateOf<Photo?>(null) }

    PaginatedLazyPhotoGrid(
        modifier = modifier.fillMaxSize(),
        data = viewModel.photos.collectAsState(),
        scrollPositionChanged = { viewModel.updateScrollPosition(it) }
    ) {
        photo.value = it
        showBottomSheet.value = true
    }

    if (showBottomSheet.value && null != photo.value) {
        PhotoDetailsBottomSheet(photo = photo.value!!) {
            showBottomSheet.value = false
        }
    }
}