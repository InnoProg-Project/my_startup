package com.innoprog.android.feature.feed.domain

import com.innoprog.android.feature.feed.domain.models.News

interface FavoritesRepository {
    suspend fun insertNewsToFavorites(news: News)
    suspend fun deleteNewsFromFavorites(news: News)
}
