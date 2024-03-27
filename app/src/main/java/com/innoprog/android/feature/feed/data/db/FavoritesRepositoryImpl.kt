package com.innoprog.android.feature.feed.data.db

import com.innoprog.android.db.RoomDB
import com.innoprog.android.feature.feed.domain.models.FavoritesRepository
import com.innoprog.android.feature.feed.domain.models.News
import com.innoprog.android.feature.feed.utils.NewsEntityMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritesRepositoryImpl @Inject constructor(
    private val appDatabase: RoomDB,
    private val newsConverter: NewsEntityMapper
) : FavoritesRepository {
    override suspend fun insertNewsToFavorites(news: News) {
        appDatabase.newsDao().insertNews(newsConverter.mapNewsToNewsEntity(news))
    }

    override suspend fun deleteNewsFromFavorites(news: News) {
        appDatabase.newsDao().deleteNews(newsConverter.mapNewsToNewsEntity(news))
    }
}
