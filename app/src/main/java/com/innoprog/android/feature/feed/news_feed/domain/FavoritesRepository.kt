package com.innoprog.android.feature.feed.news_feed.domain

import com.innoprog.android.feature.feed.news_feed.domain.models.News

interface FavoritesRepository {
    suspend fun insertNewsToFavorites(news: News)
    suspend fun deleteNewsFromFavorites(news: News)
}
