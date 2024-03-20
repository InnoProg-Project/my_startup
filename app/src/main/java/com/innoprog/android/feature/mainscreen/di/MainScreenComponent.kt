package com.innoprog.android.feature.mainscreen.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [MainScreenModule::class]
)
interface MainScreenComponent : ScreenComponent
