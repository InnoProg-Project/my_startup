package com.innoprog.android.feature.feed.newsdetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.newsdetails.data.NewsDetailsRepositoryImpl
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractor
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsInteractorImpl
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsRepository
import com.innoprog.android.feature.feed.newsdetails.presentation.NewsDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsDetailsModule {
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    @Binds
    fun bindNewsDetailsViewModel(impl: NewsDetailsViewModel): ViewModel

    @Binds
    fun bindNewsDetailsRepository(newsDetailsRepositoryImpl: NewsDetailsRepositoryImpl): NewsDetailsRepository

    @Binds
    fun bindNewsDetailsInteractor(newsDetailsInteractorImpl: NewsDetailsInteractorImpl): NewsDetailsInteractor
}
