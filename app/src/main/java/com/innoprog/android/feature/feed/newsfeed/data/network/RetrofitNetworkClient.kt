package com.innoprog.android.feature.feed.newsfeed.data.network

import android.content.Context
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.getResponse
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val apiService: FeedApi,
    private val context: Context
) : NetworkClient {

    override suspend fun loadNewsFeed(queryPage: QueryPage): Response {
        return getResponse(
            context = context,
            requestRunner = {
                apiService.loadNewsFeedQueryPage(
                    type = queryPage.type,
                    query = queryPage.query,
                    lastId = queryPage.lastId,
                    projectId = queryPage.projectId,
                    authorId = queryPage.authorId,
                    pageSize = queryPage.pageSize
                )
            },
            mapToResponse = { FeedResponse(it) }
        )
    }

    override suspend fun getProjectDetails(projectId: String): Response {
        return getResponse(
            context = context,
            requestRunner = { apiService.getProjectDetails(projectId) },
            mapToResponse = { ProjectResponse(it) }
        )
    }
}
