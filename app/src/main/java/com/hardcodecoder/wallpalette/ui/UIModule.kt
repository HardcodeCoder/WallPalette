package com.hardcodecoder.wallpalette.ui

import com.hardcodecoder.wallpalette.ui.home.HomeViewModel
import com.hardcodecoder.wallpalette.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        HomeViewModel(get())
    }
    viewModel {
        SearchViewModel(get())
    }
}