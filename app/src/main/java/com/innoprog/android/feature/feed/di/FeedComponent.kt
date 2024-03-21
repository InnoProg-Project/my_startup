package com.innoprog.android.feature.feed.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(
    modules = [FeedModule::class]
)
interface FeedComponent : ScreenComponent
