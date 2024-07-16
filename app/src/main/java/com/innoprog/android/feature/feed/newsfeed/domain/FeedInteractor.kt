package com.innoprog.android.feature.feed.newsfeed.domain

import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface FeedInteractor {
    fun getNewsFeed(queryPage: QueryPage): Flow<Resource<List<NewsWithProject>>>
}
