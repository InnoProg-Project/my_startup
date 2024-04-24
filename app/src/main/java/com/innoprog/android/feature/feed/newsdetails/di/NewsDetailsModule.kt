package com.innoprog.android.feature.feed.newsdetails.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.newsdetails.presentation.NewsDetailsViewModel
import com.innoprog.android.feature.feed.newsfeed.data.FavoritesRepositoryImpl
import com.innoprog.android.feature.feed.newsfeed.domain.FavoritesInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.FavoritesRepository
import com.innoprog.android.feature.feed.newsfeed.domain.impl.FavoritesInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface NewsDetailsModule {
    @IntoMap
    @ViewModelKey(NewsDetailsViewModel::class)
    @Binds
    fun bindFeedViewModel(impl: NewsDetailsViewModel): ViewModel

    @Binds
    fun bindFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun bindFavoritesInteractor(favoritesInteractorImpl: FavoritesInteractorImpl): FavoritesInteractor
}
