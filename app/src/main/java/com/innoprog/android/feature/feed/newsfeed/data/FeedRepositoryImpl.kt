package com.innoprog.android.feature.feed.newsfeed.data

import android.util.Log
import com.google.gson.JsonParseException
import com.innoprog.android.BuildConfig
import com.innoprog.android.feature.feed.newsfeed.data.converters.mapToNewsDomain
import com.innoprog.android.feature.feed.newsfeed.data.converters.mapToProjectDomain
import com.innoprog.android.feature.feed.newsfeed.data.network.FeedResponse
import com.innoprog.android.feature.feed.newsfeed.data.network.NetworkClient
import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectResponse
import com.innoprog.android.feature.feed.newsfeed.domain.FeedRepository
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorHandler
import com.innoprog.android.util.ErrorHandlerImpl
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    FeedRepository {
    private val errorHandler: ErrorHandler = ErrorHandlerImpl()

    override fun getNewsFeed(queryPage: QueryPage): Flow<Resource<List<NewsWithProject>>> = flow {
        try {
            val response = networkClient.loadNewsFeed(queryPage)
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    val newsList = (response as? FeedResponse)?.results ?: emptyList()
                    val newsWithProjects = newsList.map { news ->
                        val project = loadProjectDetails(news.projectId)
                        NewsWithProject(news.mapToNewsDomain(), project)
                    }
                    emit(Resource.Success(newsWithProjects))
                }

                else -> emit(errorHandler.handleErrorCode(response.resultCode))
            }
        } catch (e: HttpException) {
            Log.e(TAG, EXCEPTION_MESSAGE + e.localizedMessage)
            emit(errorHandler.handleHttpException(e))
        } catch (e: IOException) {
            Log.e(TAG, EXCEPTION_MESSAGE + e.localizedMessage)
            emit(Resource.Error(ErrorType.NO_CONNECTION))
        } catch (e: JsonParseException) {
            Log.e(TAG, EXCEPTION_MESSAGE + e.localizedMessage)
            emit(Resource.Error(ErrorType.UNEXPECTED))
        } catch (e: SocketTimeoutException) {
            Log.e(TAG, EXCEPTION_MESSAGE + e.localizedMessage)
            emit(Resource.Error(ErrorType.NO_CONNECTION))
        }
    }

    private suspend fun loadProjectDetails(projectId: String): Project? {
        val projectResponse = networkClient.getProjectDetails(projectId)

        return if (projectResponse.resultCode == ApiConstants.SUCCESS_CODE) {
            runCatching {
                (projectResponse as ProjectResponse).results.mapToProjectDomain()
            }.onFailure { exception ->
                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "mapping error -> $exception")
                    exception.printStackTrace()
                }
            }.getOrNull()
        } else {
            null
        }
    }

    companion object {
        private val TAG = FeedRepository::class.simpleName
        private const val EXCEPTION_MESSAGE = "exception -> "
    }
}
