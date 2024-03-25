package com.innoprog.android.feature.feed.domain.models

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
