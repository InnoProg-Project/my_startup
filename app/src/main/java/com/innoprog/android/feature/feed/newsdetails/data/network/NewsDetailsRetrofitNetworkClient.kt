package com.innoprog.android.feature.feed.newsdetails.data.network

import android.content.Context
import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectDto
import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectResponse
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.isInternetReachable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class NewsDetailsRetrofitNetworkClient @Inject constructor(
    private val apiService: NewsDetailsApi,
    private val context: Context
) : NewsDetailsNetworkClient {

    override suspend fun getNewsDetails(publicationId: String): Response {
        return getResponse(
            requestRunner = { apiService.getNewsDetails(publicationId) },
            mapToResponse = { NewsDetailsResponse(it) }
        )
    }

    override suspend fun getComments(publicationId: String): Response {
        return getResponse(
            requestRunner = { apiService.getComments(publicationId) },
            mapToResponse = { CommentsResponse(it) }
        )
    }

    override suspend fun getProjectDetails(projectId: String): Response {
        return getResponse<ProjectDto, ProjectResponse>(
            requestRunner = { apiService.getProjectDetails(projectId) },
            mapToResponse = { ProjectResponse(it) }
        )
    }

    override suspend fun addComment(publicationId: String, content: String): Response {
        return getResponse<CommentDto, AddCommentResponse>(
            requestRunner = { apiService.addComment(publicationId, AddCommentRequest(content)) },
            mapToResponse = { AddCommentResponse(it) }
        )
    }

    private suspend inline fun <reified D, reified R : Response> getResponse(
        crossinline requestRunner: suspend () -> retrofit2.Response<D>,
        crossinline mapToResponse: (D) -> R
    ): Response {
        if (context.isInternetReachable().not()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = requestRunner()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    mapToResponse(body).apply { resultCode = response.code() }
                } else {
                    Response().apply { resultCode = response.code() }
                }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            }
        }
    }
}
