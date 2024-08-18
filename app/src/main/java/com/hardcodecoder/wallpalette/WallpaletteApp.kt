package com.hardcodecoder.wallpalette

import android.app.Application
import com.hardcodecoder.wallpalette.data.dataModule
import com.hardcodecoder.wallpalette.ui.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WallpaletteApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.ERROR)
            androidContext(this@WallpaletteApp)
            modules(dataModule, uiModule)
        }
    }
}