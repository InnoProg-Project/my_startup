package com.innoprog.android.feature.feed.newsfeed.domain

import com.innoprog.android.feature.feed.newsfeed.domain.models.News

interface FavoritesInteractor {
    suspend fun insertNewsToFavorites(news: News)
    suspend fun deleteNewsFromFavorites(news: News)
}
