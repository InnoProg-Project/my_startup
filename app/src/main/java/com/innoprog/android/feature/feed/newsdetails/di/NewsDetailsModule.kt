package com.innoprog.android.feature.feed.newsdetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.newsdetails.presentation.NewsDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsDetailsModule {
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    @Binds
    fun bindFeedViewModel(impl: NewsDetailsViewModel): ViewModel
}
