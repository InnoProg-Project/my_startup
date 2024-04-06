package com.innoprog.android.feature.training.videoPlayer.di

import com.innoprog.android.di.ScreenComponent
import dagger.Component

@Component(modules = [VideoPlayerModule::class])
interface VideoPlayerComponent : ScreenComponent
