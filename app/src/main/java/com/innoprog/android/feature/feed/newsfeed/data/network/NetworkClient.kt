package com.innoprog.android.feature.feed.newsfeed.data.network

import com.innoprog.android.network.data.Response

interface NetworkClient {
    suspend fun loadNewsFeed(): Response
    suspend fun getProjectDetails(projectId: String): Response
}
