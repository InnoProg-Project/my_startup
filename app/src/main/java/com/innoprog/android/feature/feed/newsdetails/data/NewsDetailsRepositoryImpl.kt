package com.innoprog.android.feature.feed.newsdetails.data

import com.google.gson.JsonParseException
import com.innoprog.android.feature.feed.newsdetails.data.network.CommentsResponse
import com.innoprog.android.feature.feed.newsdetails.data.network.NetworkClient
import com.innoprog.android.feature.feed.newsdetails.data.network.NewsDetailsResponse
import com.innoprog.android.feature.feed.newsdetails.data.network.mapToCommentModel
import com.innoprog.android.feature.feed.newsdetails.data.network.mapToNewsDetails
import com.innoprog.android.feature.feed.newsdetails.domain.NewsDetailsRepository
import com.innoprog.android.feature.feed.newsdetails.domain.models.CommentModel
import com.innoprog.android.feature.feed.newsdetails.domain.models.NewsDetailsModel
import com.innoprog.android.network.data.ApiConstants
import com.innoprog.android.util.ErrorType
import com.innoprog.android.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

@Suppress("Detekt.Indentation")
class NewsDetailsRepositoryImpl @Inject constructor(private val networkClient: NetworkClient) :
    NewsDetailsRepository {
    override suspend fun getNewsDetails(id: String): Resource<NewsDetailsModel> {
        return try {
            val response = networkClient.getNewsDetails(id)
            when (response.resultCode) {
                ApiConstants.SUCCESS_CODE -> {
                    with(response as NewsDetailsResponse) {
                        val newsDetails = results.mapToNewsDetails()
                        Resource.Success(newsDetails)
                    }
                }

                else -> Resource.Error(ErrorType.BAD_REQUEST)
            }
        } catch (e: HttpException) {
            Resource.Error(ErrorType.NO_CONNECTION)
        } catch (e: IOException) {
            Resource.Error(ErrorType.NO_CONNECTION)
        } catch (e: JsonParseException) {
            Resource.Error(ErrorType.UNEXPECTED)
        } catch (e: SocketTimeoutException) {
            Resource.Error(ErrorType.NO_CONNECTION)
        }
    }

    override fun getComments(newsId: String): Flow<Resource<List<CommentModel>>> = flow {
        val response = networkClient.getComments(newsId)
        runCatching {
            when (response.resultCode) {
                ApiConstants.NO_INTERNET_CONNECTION_CODE -> {
                    emit(Resource.Error(ErrorType.NO_CONNECTION))
                }

                ApiConstants.SUCCESS_CODE -> {
                    with(response as CommentsResponse) {
                        val comments = results.map { it.mapToCommentModel() }
                        emit(Resource.Success(comments))
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
