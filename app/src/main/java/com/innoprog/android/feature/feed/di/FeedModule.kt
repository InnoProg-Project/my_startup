package com.innoprog.android.feature.feed.di

import androidx.lifecycle.ViewModel
import com.innoprog.android.di.ViewModelKey
import com.innoprog.android.feature.feed.data.db.FavoritesRepositoryImpl
import com.innoprog.android.feature.feed.domain.models.FavoritesInteractor
import com.innoprog.android.feature.feed.domain.models.FavoritesInteractorImpl
import com.innoprog.android.feature.feed.domain.models.FavoritesRepository
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

    @Binds
    fun provideFavoritesRepository(favoritesRepositoryImpl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    fun provideFavoritesInteractor(favoritesInteractorImpl: FavoritesInteractorImpl): FavoritesInteractor
}
