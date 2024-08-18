package com.hardcodecoder.wallpalette.ui

import com.hardcodecoder.wallpalette.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel {
        HomeViewModel(get())
    }
}