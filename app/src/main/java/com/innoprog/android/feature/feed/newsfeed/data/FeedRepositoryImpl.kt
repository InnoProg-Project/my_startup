package com.innoprog.android.feature.feed.newsfeed.data

import android.util.Log
import com.innoprog.android.BuildConfig
import com.innoprog.android.feature.feed.newsfeed.data.converters.mapToNewsDomain
import com.innoprog.android.feature.feed.newsfeed.data.converters.mapToProjectDomain
import com.innoprog.android.feature.feed.newsfeed.data.network.FeedResponse
import com.innoprog.android.feature.feed.newsfeed.data.network.NetworkClient
import com.innoprog.android.feature.feed.newsfeed.data.network.ProjectResponse
import com.innoprog.android.feature.feed.newsfeed.domain.FeedRepository
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.feature.feed.newsfeed.domain.models.NewsWithProject
import com.innoprog.android.feature.feed.newsfeed.domain.models.Project
import com.innoprog.android.feature.feed.newsfeed.domain.models.QueryPage
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    FeedRepository {

    override fun getNewsFeed(queryPage: QueryPage): Flow<Resource<List<NewsWithProject>>> = flow {
        val newsResult = loadNewsList(queryPage)

        if (newsResult is Resource.Error) {
            emit(newsResult)
        } else if (newsResult is Resource.Success) {
            val newsList = newsResult.data

            val newsWithProjects = newsList.map { news ->
                val project = if (news.projectId != null) {
                    loadProjectDetails(news.projectId)
                } else {
                    null
                }
                NewsWithProject(news, project)
            }
            emit(Resource.Success(newsWithProjects))
        }
    }

    private suspend fun loadNewsList(queryPage: QueryPage): Resource<List<News>> {
        val newsResponse = networkClient.loadNewsFeed(queryPage)

        return runCatching {
            when (newsResponse.resultCode) {
                ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                    Resource.Error(ErrorType.NO_CONNECTION)
                }

                ApiConstants.SUCCESS_CODE -> {
                    with(newsResponse as FeedResponse) {
                        val newsList = results.map { it.mapToNewsDomain() }
                        Resource.Success(newsList)
                    }
                }

                else -> {
                    Resource.Error(ErrorType.BAD_REQUEST)
                }
            }
        }.getOrElse { exception ->
            if (exception is SocketTimeoutException) {
                Resource.Error(ErrorType.NO_CONNECTION)
            } else {
                Resource.Error(ErrorType.UNEXPECTED)
            }
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
    }
}
