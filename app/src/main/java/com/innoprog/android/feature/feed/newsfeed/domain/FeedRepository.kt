package com.innoprog.android.feature.feed.newsfeed.domain

import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getNewsFeed(type: String? = null): Flow<Resource<List<NewsWithProject>>>
}
