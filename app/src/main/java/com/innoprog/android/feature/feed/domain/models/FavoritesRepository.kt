package com.innoprog.android.feature.feed.domain.models

interface FavoritesRepository {
    suspend fun insertNewsToFavorites(news: News)
    suspend fun deleteNewsFromFavorites(news: News)
}
