package com.innoprog.android.feature.feed.newsfeed.domain

import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
    fun getNewsFeed(): Flow<Resource<List<News>>>
    suspend fun getProjectDetails(projectId: String): Resource<Project>
}
