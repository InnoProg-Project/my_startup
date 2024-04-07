package com.innoprog.android.feature.profile.aboutapp.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [AboutAppModule::class]
)
interface AboutAppComponent : ScreenComponent
