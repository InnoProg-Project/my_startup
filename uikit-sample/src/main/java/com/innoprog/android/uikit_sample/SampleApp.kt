package com.innoprog.android.uikit_sample

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class SampleApp : Application() {
    private var darkTheme = false
    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }
}