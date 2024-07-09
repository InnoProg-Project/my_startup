package com.innoprog.android.feature.feed.newsfeed.domain.impl

import com.innoprog.android.feature.feed.newsfeed.domain.FeedInteractor
import com.innoprog.android.feature.feed.newsfeed.domain.FeedRepository
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedInteractorImpl @Inject constructor(private val repository: FeedRepository) :
    FeedInteractor {
    override fun getNewsFeed(queryPage: QueryPage): Flow<Resource<List<NewsWithProject>>> {
        return repository.getNewsFeed(queryPage)
    }
}
