package com.app.sehatcare

import android.app.Application
import com.app.sehatcare.common.managers.ThemeManager
import com.app.sehatcare.core.network.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject lateinit var themeManager: ThemeManager

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.ENABLE_LOGGING) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }

        themeManager.applyTheme(themeManager.getSavedTheme())
    }
}
