package com.innoprog.android.feature.profile.di

import com.innoprog.android.di.ApiModule
import com.innoprog.android.di.NetworkModule
import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [ProfileModule::class, NetworkModule::class, ApiModule::class]
)
interface ProfileComponent : ScreenComponent
