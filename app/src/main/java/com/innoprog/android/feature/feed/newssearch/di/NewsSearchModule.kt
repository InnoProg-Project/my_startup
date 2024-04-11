package com.innoprog.android.feature.feed.newssearch.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.newssearch.presentation.NewsSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsSearchModule {
    @IntoMap
    @ViewModelKey(NewsSearchViewModel::class)
    @Binds
    fun bindNewsSearchViewModel(impl: NewsSearchViewModel): ViewModel
}
