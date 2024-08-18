package com.hardcodecoder.wallpalette.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.hardcodecoder.wallpalette.ui.core.PaginatedLazyPhotoGrid
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<HomeViewModel>()
    PaginatedLazyPhotoGrid(
        modifier = modifier.fillMaxSize(),
        data = viewModel.photos.collectAsState(),
        onReachedEnd = { viewModel.updateScrollPosition(it) }
    ) {
    }
}