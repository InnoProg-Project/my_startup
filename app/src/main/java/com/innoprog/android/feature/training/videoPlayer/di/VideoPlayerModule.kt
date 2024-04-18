package com.innoprog.android.feature.training.videoPlayer.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.training.videoPlayer.presentation.VideoPlayerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface VideoPlayerModule {

    @IntoMap
    @ViewModelKey(VideoPlayerViewModel::class)
    @Binds
    fun bindVideoPlayerViewModel(impl: VideoPlayerViewModel): ViewModel
}
