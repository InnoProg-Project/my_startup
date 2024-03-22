package com.innoprog.android.feature.feed.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.presentation.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FeedModule {

    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    @Binds
    fun bindFeedViewModel(impl: FeedViewModel): ViewModel
}
