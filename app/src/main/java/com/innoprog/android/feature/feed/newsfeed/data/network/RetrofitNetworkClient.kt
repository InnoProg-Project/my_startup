package com.innoprog.android.feature.feed.newsfeed.data.network

import android.content.Context
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.network.data.Response
import com.innoprog.android.util.isInternetReachable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitNetworkClient @Inject constructor(
    private val apiService: FeedApi,
    private val context: Context
) : NetworkClient {

    override suspend fun loadNewsFeed(queryPage: QueryPage): Response {
        if (context.isInternetReachable().not()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.loadNewsFeedQueryPage(
                    type = queryPage.type,
                    query = queryPage.query,
                    lastId = queryPage.lastId,
                    projectId = queryPage.projectId,
                    authorId = queryPage.authorId,
                    pageSize = queryPage.pageSize
                )
                FeedResponse(results = response.body()!!).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            }
        }
    }

    override suspend fun getProjectDetails(projectId: String): Response {
        if (context.isInternetReachable().not()) {
            return Response().apply { resultCode = ApiConstants.NO_INTERNET_CONNECTION_CODE }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProjectDetails(projectId)
                ProjectResponse(results = response.body()!!).apply {
                    resultCode = ApiConstants.SUCCESS_CODE
                }
            } catch (exception: HttpException) {
                Response().apply { resultCode = exception.code() }
            }
        }
    }
}
