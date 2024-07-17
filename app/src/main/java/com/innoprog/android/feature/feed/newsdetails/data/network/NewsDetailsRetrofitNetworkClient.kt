package com.innoprog.android.feature.feed.newsdetails.data.network

import android.content.Context
import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectDto
import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectResponse
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.getResponse
import javax.inject.Inject

class NewsDetailsRetrofitNetworkClient @Inject constructor(
    private val apiService: NewsDetailsApi,
    private val context: Context
) : NewsDetailsNetworkClient {

    override suspend fun getNewsDetails(publicationId: String): Response {
        return getResponse(
            context = context,
            requestRunner = { apiService.getNewsDetails(publicationId) },
            mapToResponse = { NewsDetailsResponse(it) }
        )
    }

    override suspend fun getComments(publicationId: String): Response {
        return getResponse(
            context = context,
            requestRunner = { apiService.getComments(publicationId) },
            mapToResponse = { CommentsResponse(it) }
        )
    }

    override suspend fun getProjectDetails(projectId: String): Response {
        return getResponse<ProjectDto, ProjectResponse>(
            context = context,
            requestRunner = { apiService.getProjectDetails(projectId) },
            mapToResponse = { ProjectResponse(it) }
        )
    }

    override suspend fun addComment(publicationId: String, content: String): Response {
        return getResponse<CommentDto, AddCommentResponse>(
            context = context,
            requestRunner = { apiService.addComment(publicationId, AddCommentRequest(content)) },
            mapToResponse = { AddCommentResponse(it) }
        )
    }
}
