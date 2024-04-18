package com.innoprog.android.feature.feed.newsfeed.domain.impl

import com.innoprog.android.feature.feed.newsfeed.domain.FavoritesInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.FavoritesRepository
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
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
