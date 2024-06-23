package com.innoprog.android.feature.feed.newsfeed.data

import com.innoprog.android.feature.feed.newsfeed.data.converters.NewsDtoMapper
import com.innoprog.android.feature.feed.newsfeed.data.network.FeedResponse
import com.innoprog.android.feature.feed.newsfeed.data.network.NetworkClient
import com.innoprog.android.feature.feed.newsfeed.domain.FeedRepository
import com.innoprog.android.feature.feed.newsfeed.domain.models.News
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.SocketTimeoutException
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    FeedRepository {

    override fun getNewsFeed(): Flow<Resource<List<News>>> = flow {
        val response = networkClient.loadNewsFeed()

        runCatching {
            when (response.resultCode) {
                ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                    emit(Resource.Error(ErrorType.NO_CONNECTION))
                }

                ApiConstants.SUCCESS_CODE -> {
                    with(response as FeedResponse) {
                        val newsDtoMapper = NewsDtoMapper()
                        val data = results.map { newsDtoMapper.newsDtoToNews(it) }
                        emit(Resource.Success(data))
                    }
                }

                else -> {
                    emit(Resource.Error(ErrorType.BAD_REQUEST))
                }
            }
        }.onFailure { exception ->
            if (exception is SocketTimeoutException) {
                emit(Resource.Error(ErrorType.NO_CONNECTION))
            } else {
                emit(Resource.Error(ErrorType.UNEXPECTED))
            }
        }
    }
}
