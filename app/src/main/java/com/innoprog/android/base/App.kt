package com.innoprog.android.base

import android.app.Application
import com.innoprog.android.di.AppComponentHolder

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponentHolder.createComponent(this)
    }
}