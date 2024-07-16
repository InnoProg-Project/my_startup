package com.innoprog.android.feature.feed.newsfeed.data.network

import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.network.data.Response

interface NetworkClient {
    suspend fun loadNewsFeed(queryPage: QueryPage): Response
    suspend fun getProjectDetails(projectId: String): Response
}
