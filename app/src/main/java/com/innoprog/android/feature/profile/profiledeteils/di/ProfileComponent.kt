package com.innoprog.android.feature.profile.profiledeteils.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [ProfileModule::class]
)
interface ProfileComponent : ScreenComponent
