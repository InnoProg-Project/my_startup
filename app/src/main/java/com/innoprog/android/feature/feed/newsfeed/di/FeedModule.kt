package com.innoprog.android.feature.feed.newsfeed.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.newsfeed.data.FavoritesRepositoryImpl
import com.innoprog.android.feature.feed.newsfeed.domain.FavoritesInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.impl.FavoritesInteractorImpl
import com.innoprog.android.feature.feed.newsfeed.domain.FavoritesRepository
import com.innoprog.android.feature.feed.newsfeed.presentation.FeedViewModel
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
    fun bindFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun bindFavoritesInteractor(favoritesInteractorImpl: FavoritesInteractorImpl): FavoritesInteractor
}
