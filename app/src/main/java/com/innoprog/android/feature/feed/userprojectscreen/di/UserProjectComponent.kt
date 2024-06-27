package com.innoprog.android.feature.feed.userprojectscreen.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [UserProjectModule::class]
)
interface UserProjectComponent : ScreenComponent