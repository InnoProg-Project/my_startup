package com.innoprog.android.feature.feed.news_feed.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.news_feed.data.FavoritesRepositoryImpl
import com.innoprog.android.feature.feed.news_feed.domain.FavoritesInteractor
import com.innoprog.android.feature.feed.news_feed.domain.impl.FavoritesInteractorImpl
import com.innoprog.android.feature.feed.news_feed.domain.FavoritesRepository
import com.innoprog.android.feature.feed.news_feed.presentation.FeedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface FeedModule {
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    @Binds
    fun bindFeedViewModel(impl: FeedViewModel): ViewModel

    @Binds
    fun provideFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun provideFavoritesInteractor(favoritesInteractorImpl: FavoritesInteractorImpl): FavoritesInteractor
}
