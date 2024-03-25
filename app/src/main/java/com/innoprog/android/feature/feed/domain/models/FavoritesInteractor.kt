package com.innoprog.android.feature.feed.domain.models

interface FavoritesInteractor {
    suspend fun insertNewsToFavorites(news: News)
    suspend fun deleteNewsFromFavorites(news: News)
}
