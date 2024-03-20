package com.innoprog.android.feature.profile.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [ProfileModule::class]
)
interface ProfileComponent : ScreenComponent
