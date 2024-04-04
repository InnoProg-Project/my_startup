package com.innoprog.android.feature.feed.domain.impl

import com.innoprog.android.feature.feed.domain.FavoritesInteractor
import com.innoprog.android.feature.feed.domain.FavoritesRepository
import com.innoprog.android.feature.feed.domain.models.News
import javax.inject.Inject

class FavoritesInteractorImpl @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : FavoritesInteractor {
    override suspend fun insertNewsToFavorites(news: News) {
        favoritesRepository.insertNewsToFavorites(news)
    }

    override suspend fun deleteNewsFromFavorites(news: News) {
        favoritesRepository.deleteNewsFromFavorites(news)
    }
}